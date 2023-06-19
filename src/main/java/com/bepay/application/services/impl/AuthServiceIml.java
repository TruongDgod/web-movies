package com.bepay.application.services.impl;

import com.bepay.application.config.GlobalConfig;
import com.bepay.application.config.MenuConfig;
import com.bepay.application.controllers.BaseController;
import com.bepay.application.dto.response.UserResponse;
import com.bepay.application.entities.db.AccountEntity;
import com.bepay.application.mapper.UserLoginMapper;
import com.bepay.application.models.auth.*;
import com.bepay.application.repository.db.AccountRepository;
import com.bepay.application.services.AuthService;
import com.bepay.application.utils.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.net.ssl.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author DanTran
 * @cretaeDate 15/11/2021
 * @See AuthServiceIml
 */

@Service
public class AuthServiceIml extends AbstractServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceIml.class);

    @Autowired
    private GlobalConfig globalConfig;

    @Autowired
    private MenuConfig menuConfig;

    @Autowired
    private BaseController baseController;

    @Autowired
    private UserLoginMapper userLoginMappper;

    @Autowired
    private AccountRepository accountRepository;

    static {
        disableSslVerification();
    }

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        logger.info("#AuthServiceIml.login START -- ");
        UserLoginInfo userLoginInfo = null;

        try {
            String passportUrl = globalConfig.getPassportUrl();
            String domainValue = globalConfig.getDomainCodeValue();
            String request = contentRequest(loginRequest, domainValue);
            userLoginInfo = parseXMLResponse(passportUrl, request);
            AccountEntity accountEntity = accountRepository.findByUserName(loginRequest.getUsername());
            if( accountEntity == null){
//                MessageDigest md = MessageDigest.getInstance("MD5");
//                md.update(loginRequest.getPassword().getBytes());
//                byte[] digest = md.digest();
//                String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
                accountEntity = new AccountEntity(loginRequest.getUsername(), loginRequest.getPassword());
                accountRepository.saveAndFlush(accountEntity);
            } else if(!accountEntity.getPassWord().equals(loginRequest.getPassword())){
                accountEntity.setPassWord(loginRequest.getPassword());
                accountRepository.saveAndFlush(accountEntity);
            }
            return userLoginMappper.convertToResponse(userLoginInfo);
        } catch (Exception e) {
            logger.error("#AuthServiceIml.login -------: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private UserLoginInfo parseXMLResponse(String passportUrl, String request) {
        logger.info("#AuthServiceIml.parseXMLResponse START --  ");
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        try {

            URL myurl = new URL(passportUrl);
            InputStream ins = disableSslVerification(myurl, request);
            InputStreamReader isr = new InputStreamReader(ins);
            BufferedReader in = new BufferedReader(isr);

            String result;
            String inputLine;
            for (result = ""; (inputLine = in.readLine()) != null; result = result + inputLine) {
            }
            in.close();

            int start = result.indexOf("<ns2:validateResponse") + "<ns2:validateResponse".length();
            int end = result.indexOf("</ns2:validateResponse>");
            String returnXml = result.substring(start, end);
            returnXml = returnXml.replaceAll("&lt;", "<")
                    .replaceAll("&gt;", ">")
                    .replaceAll("&quot;", "\"");

            int startReturn = returnXml.indexOf("<return>") + "<return>".length();
            int endReturn = returnXml.indexOf("</return>");
            String response = returnXml.substring(startReturn, endReturn);

            if (response == null || "no".equals(response)) {
                return null;
            }

            userLoginInfo = parseXML(response, userLoginInfo);

        } catch (Exception e) {
            logger.info("#AuthServiceIml.parseXMLResponse Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return userLoginInfo;
    }

    private UserLoginInfo parseXML(String response, UserLoginInfo userLoginInfo) {
        logger.info("#AuthServiceIml.parseXML START --  ");
        try {
            if (response.equalsIgnoreCase("no")) {
                return null;
            } else {

                UserInfo userInfo = new UserInfo();
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = db.parse(new InputSource(new StringReader(response)));
                NodeList nl = doc.getElementsByTagName("UserData");
                Element roleEle;
                if (nl != null && nl.getLength() > 0) {
                    Element userEle = (Element) nl.item(0);
                    NodeList nlUsers = userEle.getElementsByTagName("Row");
                    if (nlUsers != null && nlUsers.getLength() > 0) {
                        roleEle = (Element) nlUsers.item(0);
                        userInfo = getUserInfo(roleEle);
                        userLoginInfo.setUserInfo(userInfo);
                    }
                }

                List<Roles> rolesList = new ArrayList();
                nl = doc.getElementsByTagName("Roles");
                if (nl != null && nl.getLength() > 0) {
                    roleEle = (Element) nl.item(0);
                    NodeList nlRoles = roleEle.getElementsByTagName("Row");
                    if (nlRoles != null && nlRoles.getLength() > 0) {
                        for (int i = 0; i < nlRoles.getLength(); ++i) {
                            Element el = (Element) nlRoles.item(i);
                            Roles roles = getRolesList(el);
                            rolesList.add(roles);
                        }
                        userLoginInfo.setRoles(rolesList);
                    }
                }

                List<Menus> permissionList = new ArrayList();
                nl = doc.getElementsByTagName("ObjectAll");
                if (nl != null && nl.getLength() > 0) {
                    Element objectEle = (Element) nl.item(0);
                    NodeList nlObjects = objectEle.getElementsByTagName("Row");
                    if (nlObjects != null && nlObjects.getLength() > 0) {
                        for (int i = 0; i < nlObjects.getLength(); ++i) {
                            Element el = (Element) nlObjects.item(i);
                            Menus permission = getMenu(el);
                            permissionList.add(permission);
                        }
                    }
                }

                Collections.sort(permissionList);
                userLoginInfo.setListPermissionComponent(permissionList);

                List<MenuConfig.Menu> listMenu = menuConfig.getMenus();
                List<String> listMenuAccess = new ArrayList<>();
                for (int i = 0; i < permissionList.size(); i++) {
                    for (int j = 0; j < listMenu.size(); j++) {
                        if (permissionList.get(i).getObjectCode().equals(listMenu.get(j).getCode())) {
                            permissionList.get(i).setNameUS(listMenu.get(j).getName());
//                            permissionList.get(i).setNameVN(baseController.getValueByKeyLanguage(listMenu.get(j).getName(), "vi", "VN"));
                            permissionList.get(i).setIcons(listMenu.get(j).getIcon());
                            listMenuAccess.add(permissionList.get(i).getObjectUrl());
                            break;
                        }
                    }
                }

                List<Menus> listParentMenu = new ArrayList<>();
                List<Menus> listMenuChild = new ArrayList<>();
                for (int i = 0; i < permissionList.size(); i++) {
                    Menus permission = permissionList.get(i);
                    if (permission.getObjectTypeId() == 0 && permission.getParentId() == -1) {
                        listParentMenu.add(permission);
                    } else {
                        listMenuChild.add(permission);
                    }
                }

                List<Menus> menuComponent = new ArrayList<>();
                for (int j = 0; j < listMenuChild.size(); j++) {
                    Menus permissionChild = listMenuChild.get(j);
                    if (permissionChild.getObjectTypeId() == 0) {
                        menuComponent.add(permissionChild);
                    }
                }

                for (int j = 0; j < menuComponent.size(); j++) {
                    List<Menus> component = new ArrayList<>();
                    for (int k = 0; k < listMenuChild.size(); k++) {
                        if (menuComponent.get(j).getObjectId() == listMenuChild.get(k).getParentId()) {
                            component.add(listMenuChild.get(k));
                            menuComponent.get(j).setListMenuComponent(component);
                        }
                    }
                }

                for (int i = 0; i < listParentMenu.size(); i++) {
                    List<Menus> childMenu = new ArrayList<>();
                    List<Menus> childComponent = new ArrayList<>();
                    for (int j = 0; j < listMenuChild.size(); j++) {
                        Menus permissionChild = listMenuChild.get(j);
                        if (permissionChild.getObjectTypeId() == 0 && listParentMenu.get(i).getObjectId() == permissionChild.getParentId()) {
                            childMenu.add(permissionChild);
                            listParentMenu.get(i).setListMenuChild(childMenu);
                        }
                        if (permissionChild.getObjectTypeId() == 1 && listParentMenu.get(i).getObjectId() == permissionChild.getParentId()) {
                            childComponent.add(permissionChild);
                            listParentMenu.get(i).setListMenuComponent(childComponent);
                        }
                    }

                    userLoginInfo.setListMenuParent(listParentMenu);
                    userLoginInfo.setListMenuAccess(listMenuAccess);

                }
            }

        } catch (Exception e) {
            logger.error("#AuthServiceIml.parseXML Exception: " + e);
            e.printStackTrace();
        }
        return userLoginInfo;
    }

    private Menus getMenu(Element el) {
        logger.info("#AuthServiceIml.getMenu START --  ");
        Menus permission = new Menus();
        permission.setObjectId(XMLUtil.getLongValue(el, "OBJECT_ID"));
        permission.setParentId(XMLUtil.getLongValue(el, "PARENT_ID"));
        permission.setAppId(XMLUtil.getIntValue(el, "APP_ID"));
        permission.setStatus(XMLUtil.getIntValue(el, "STATUS"));
        permission.setObjectUrl(XMLUtil.getTextValue(el, "OBJECT_URL"));
        permission.setObjectName(XMLUtil.getTextValue(el, "OBJECT_NAME"));
        permission.setObjectTypeId(XMLUtil.getIntValue(el, "OBJECT_TYPE_ID"));
        permission.setObjectCode(XMLUtil.getTextValue(el, "OBJECT_CODE"));
        permission.setObjectLevel(XMLUtil.getTextValue(el, "OBJECT_LEVEL"));
        permission.setCreateDate(XMLUtil.getTextValue(el, "CREATE_DATE"));
        return permission;
    }

    private Roles getRolesList(Element el) {
        logger.info("#AuthServiceIml.getRolesList START --  ");
        Roles roles = new Roles();
        roles.setStatus(XMLUtil.getIntValue(el, "STATUS"));
        roles.setRoleId(XMLUtil.getIntValue(el, "ROLE_ID"));
        roles.setRoleName(XMLUtil.getTextValue(el, "ROLE_NAME"));
        roles.setDescription(XMLUtil.getTextValue(el, "DESCRIPTION"));
        roles.setRoleCode(XMLUtil.getTextValue(el, "ROLE_CODE"));
        roles.setCreateDate(XMLUtil.getTextValue(el, "CREATE_DATE"));
        roles.setCreatorId(XMLUtil.getIntValue(el, "CREATOR_ID"));
        roles.setCreatorName(XMLUtil.getTextValue(el, "CREATOR_NAME"));
        roles.setIpOfficeWan(XMLUtil.getIntValue(el, "IP_OFFICE_WAN"));
        return roles;
    }

    private UserInfo getUserInfo(Element userEle) {
        logger.info("#AuthServiceIml.getUserInfo START --  ");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(XMLUtil.getLongValue(userEle, "USER_ID"));
        userInfo.setUserRight(XMLUtil.getIntValue(userEle, "USER_RIGHT"));
        userInfo.setUsername(XMLUtil.getTextValue(userEle, "USER_NAME"));
        userInfo.setPassword(XMLUtil.getTextValue(userEle, "PASSWORD"));
        userInfo.setStatus(XMLUtil.getIntValue(userEle, "STATUS"));
        userInfo.setEmail(XMLUtil.getTextValue(userEle, "EMAIL"));
        userInfo.setCellPhone(XMLUtil.getTextValue(userEle, "CELLPHONE"));
        userInfo.setGender(XMLUtil.getIntValue(userEle, "GENDER"));
        userInfo.setIdentityCard(XMLUtil.getTextValue(userEle, "IDENTITY_CARD"));
        userInfo.setFullName(XMLUtil.getTextValue(userEle, "FULL_NAME"));
        userInfo.setUserTypeId(XMLUtil.getTextValue(userEle, "USER_TYPE_ID"));
        userInfo.setCreateDate(XMLUtil.getTextValue(userEle, "CREATE_DATE"));
        userInfo.setManagerId(XMLUtil.getIntValue(userEle, "MANAGER_ID"));
        userInfo.setPassWordChange(XMLUtil.getIntValue(userEle, "PASSWORDCHANGED"));
        userInfo.setProfileId(XMLUtil.getTextValue(userEle, "PROFILE_ID"));
        userInfo.setLastResetPassword(XMLUtil.getTextValue(userEle, "LAST_RESET_PASSWORD"));
        userInfo.setIp(XMLUtil.getTextValue(userEle, "IP"));
        userInfo.setDeptId(XMLUtil.getIntValue(userEle, "DEPT_ID"));
        userInfo.setDeptLevel(XMLUtil.getTextValue(userEle, "DEPT_LEVEL"));
        userInfo.setPostId(XMLUtil.getIntValue(userEle, "POS_ID"));
        userInfo.setDeptName(XMLUtil.getTextValue(userEle, "DEPT_NAME"));
        userInfo.setIgnoreCheckIp(XMLUtil.getIntValue(userEle, "IGNORE_CHECK_IP"));
        userInfo.setCheckValidTime(XMLUtil.getIntValue(userEle, "CHECK_VALID_TIME"));
        userInfo.setStartTimeToChangePassword(XMLUtil.getTextValue(userEle, "START_TIME_TO_CHANGE_PASSWORD"));
        userInfo.setIpLan(XMLUtil.getTextValue(userEle, "IP_LAN"));
        userInfo.setCheckIp(XMLUtil.getIntValue(userEle, "CHECK_IP"));
        userInfo.setCheckIpLan(XMLUtil.getIntValue(userEle, "CHECK_IP_LAN"));
        userInfo.setUseSalt(XMLUtil.getIntValue(userEle, "USE_SALT"));
        userInfo.setLoginFailAllow(XMLUtil.getIntValue(userEle, "LOGIN_FAIL_ALLOW"));
        userInfo.setTemporaryLockTime(XMLUtil.getIntValue(userEle, "TEMPORARY_LOCK_TIME"));
        userInfo.setMaxTmpLockADay(XMLUtil.getIntValue(userEle, "MAX_TMP_LOCK_ADAY"));
        userInfo.setPasswordValidTime(XMLUtil.getIntValue(userEle, "PASSWORD_VALID_TIME"));
        userInfo.setUserValidTime(XMLUtil.getIntValue(userEle, "USER_VALID_TIME"));
        userInfo.setAllowMultiIpLogin(XMLUtil.getIntValue(userEle, "ALLOW_MULTI_IP_LOGIN"));
        userInfo.setAllowLoginTimeStart(XMLUtil.getIntValue(userEle, "ALLOW_LOGIN_TIME_START"));
        userInfo.setAllowLoginTimeEnd(XMLUtil.getIntValue(userEle, "ALLOW_LOGIN_TIME_END"));
        userInfo.setId(XMLUtil.getIntValue(userEle, "ID"));
        userInfo.setName(XMLUtil.getTextValue(userEle, "NAME"));
        userInfo.setNeedChangePassword(XMLUtil.getIntValue(userEle, "NEED_CHANGE_PASSWORD"));
        userInfo.setTimeToChangePassword(XMLUtil.getIntValue(userEle, "TIME_TO_CHANGE_PASSWORD"));
        return userInfo;
    }

    private String contentRequest(LoginRequest loginRequest, String domainValue) {
        StringBuilder xml = new StringBuilder();
        xml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:pas=\"http://passport.viettel.com/\">" +
                "           <soapenv:Header/>" +
                "           <soapenv:Body>" +
                "       <pas:validate>");
        xml.append("<userName>" + loginRequest.getUsername().trim() + "</userName>");
        xml.append("<password>" + loginRequest.getPassword().trim() + "</password>");
        xml.append("<domainCode>" + domainValue + "</domainCode>");
        xml.append("    </pas:validate>" +
                "       </soapenv:Body>" +
                "   </soapenv:Envelope>");
        return xml.toString();
    }

    private static InputStream disableSslVerification(URL myurl, String request) {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
            if (con instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) con;
                httpsConn.setHostnameVerifier(allHostsValid);
                httpsConn.setSSLSocketFactory(sc.getSocketFactory());
            }
            con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            con.setDoOutput(true);
            con.getOutputStream().write(request.getBytes());
            con.getOutputStream().flush();
            InputStream ins = con.getInputStream();
            return ins;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
