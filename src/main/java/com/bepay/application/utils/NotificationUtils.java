package com.bepay.application.utils;

import com.bepay.application.config.FirebaseConfig;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationUtils {

    @Autowired
    FirebaseConfig config;


    /**
     * addValueEventListener: get value realtime
     * addListenerForSingleValueEvent: get value one time
     */


//    public void increaseNotification(String id) {
//        DatabaseReference ref = FirebaseDatabase.getInstance()
//                .getReference(config.getNodeChild() + "/" + id);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Integer data = dataSnapshot.getValue(Integer.class);
//                updateNotification(id, ++data);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//            }
//        });
//    }
//
//    public void increaseNotification(String id, Integer number) {
//        DatabaseReference ref = FirebaseDatabase.getInstance()
//                .getReference(config.getNodeChild() + "/" + id);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Integer data = dataSnapshot.getValue(Integer.class);
//                updateNotification(id, data + number);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//            }
//        });
//    }

    public void setZeroNotification(String id) {
        updateNotification(id, 0,false);
    }

    public void setZeroAlert(String id) {
        updateAlert(id, 0,false);
    }


    public void updateNotification(String id) {
        updateNotification(id, 1,true);
    }

    public void updateAlert(String id) {
        updateAlert(id,1,true);
    }

    public void updateNotification(String id,Integer number) {
        updateNotification(id, number,true);
    }

    public void updateAlert(String id,Integer number) {
        updateAlert(id,number,true);
    }

    public void createNotification(String id) {
        updateNotification(id, 1,false);
    }

    public void createAlert(String id) {
        updateAlert(id,1,false);
    }

    public void createNotification(String id,Integer number) {
        updateNotification(id, number,false);
    }

    public void createAlert(String id,Integer number) {
        updateAlert(id,number,false);
    }

    public void updateNotification(String id, Integer number,boolean update) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(config.getNodeChild());
        DatabaseReference upvotesRef = ref.child(id + "/" + config.getNotification());
        upvotesRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer currentValue = mutableData.getValue(Integer.class);
                if (currentValue == null) {
                    mutableData.setValue(number);
                } else {
                    if (update) {
                        mutableData.setValue(currentValue + number);
                    } else {
                        mutableData.setValue(number);
                    }
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(
                    DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
            }
        });
    }

    public void updateAlert(String id, Integer number, boolean update) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(config.getNodeChild());
        DatabaseReference upvotesRef = ref.child(id + "/" + config.getAlert());
        upvotesRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer currentValue = mutableData.getValue(Integer.class);
                if (currentValue == null) {
                    mutableData.setValue(number);
                } else {
                    if (update) {
                        mutableData.setValue(currentValue + number);
                    } else {
                        mutableData.setValue(number);
                    }
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(
                    DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
            }
        });
    }
}
