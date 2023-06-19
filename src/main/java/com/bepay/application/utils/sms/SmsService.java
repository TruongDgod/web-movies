//package com.bepay.application.utils.sms;
//
//import com.bepay.application.enums.LanguageEnum;
//import com.bepay.application.utils.AppUtil;
//import com.bepay.application.utils.mail.MailService;
//import kh.com.emoney.msgclient.ChannelType;
//import kh.com.emoney.msgclient.MessagingClient;
//import kh.com.emoney.msgclient.beans.Message;
//import kh.com.emoney.msgclient.beans.MessageContent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//public class SmsService {
//
//    /**
//     * @author DanTran
//     * @see MailService
//     */
//
//    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
//
//    public static void sendSms(List<String> phoneNumbers, String message, String refId) {
//        try {
//            for (int i = 0; i < phoneNumbers.size(); i++) {
//                String msisdn = phoneNumbers.get(i);
//                MessageContent mc = MessageContent.builder()
//                        .addTemplate(LanguageEnum.ENGLISH.key(), message)
//                        .addTemplate(LanguageEnum.KHMER.key(), message)
//                        .addTemplate(LanguageEnum.VIETNAMESE.key(), message)
//                        .addTemplate(LanguageEnum.CHINESE.key(), message)
//                        .addTitles(MessagingClient.shared().getApsTitle("amlSystem"))
//                        .build();
//
//                Message msg = Message.builder()
//                        .setChannel(ChannelType.MobileGateway.code())
//                        .setRefId(refId)
//                        .setReceiverObj("Manager")
//                        .setSender(MessagingClient.shared().getSender())
//                        .setReceiver(msisdn)
//                        .setMessageContent(mc)
//                        .setPushNotification(false)
//                        .setSaveNotificationLog(false)
//                        .build();
//                StringBuilder logStr = new StringBuilder();
//                logStr.append("\n=======================================================================");
//                logStr.append("\n msisdn: ").append(msisdn);
//                logStr.append("\n message: ").append(message);
//                logStr.append("\n=======================================================================");
//                logger.info(logStr.toString());
//                if (AppUtil.isNullOrEmpty(msisdn) || AppUtil.isNullOrEmpty(message)) {
//                    return;
//                }
//                MessagingClient.shared().sendAsyncTask(msg);
//            }
//        } catch (Exception ex) {
//            logger.error("#sendSms - ERROR", ex);
//            ex.printStackTrace();
//        }
//    }
//}
