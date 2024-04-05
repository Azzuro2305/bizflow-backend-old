package org.project.final_backend.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.project.final_backend.domain.request.chat.NewMessageRequest;
import org.project.final_backend.dto.request.NewChatBoxRequest;
import org.project.final_backend.entity.ChatMessage;
import org.project.final_backend.repo.ChatBoxRepo;
import org.project.final_backend.service.ChatBoxService;
import org.project.final_backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatBoxRepo chatBoxRepo;

    @Autowired
    private ChatBoxService chatBoxService;

//    @Override
//    public void createMessage(String chatId, String senderId, String recipientId, String message) {
//        Firestore db = FirestoreClient.getFirestore();
//
//        Map<String, Object> docData = new HashMap<>();
//        docData.put("senderId", senderId);
//        docData.put("recipientId", recipientId);
//        docData.put("message", message);
//        docData.put("timestamp", Timestamp.now());
//
//        try {
//            db.collection("chats").document(chatId).collection("messages").add(docData).get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    public void createMessage(String chatId, String senderId, String recipientId, String content) {
//        if (chatId == null) {
//            throw new IllegalArgumentException("chatId cannot be null");
//        }
//        Firestore db = FirestoreClient.getFirestore();
//        DocumentReference docRef = db.collection(userId).document(chatId).collection("messages").document();
//        ChatMessage message = new ChatMessage(chatId, senderId, recipientId, content);
//        ApiFuture<WriteResult> result = docRef.set(message);
//    }

//    @Override
//    public void createMessage(String chatId, String senderId, String recipientId, String content) {
//        String collectionName = senderId + "AND" + recipientId;
//        if (chatId == null) {
//            throw new IllegalArgumentException("chatId cannot be null");
//        }
//        Firestore db = FirestoreClient.getFirestore();
//        DocumentReference docRef = db.collection(collectionName).document();
//        ChatMessage message = new ChatMessage(chatId, senderId, recipientId, content);
//        ApiFuture<WriteResult> result = docRef.set(message);
//    }


//    @Override
//    public void sendMessage(NewMessageRequest request) {
//        String collectionName = request.getSenderId() + "AND" + request.getRecipientId();
//        Firestore db = FirestoreClient.getFirestore();
//        DocumentReference docRef = db.collection(collectionName).document();
//        ChatMessage message = new ChatMessage(request.getSenderId(), request.getRecipientId(), request.getContent());
//        ApiFuture<WriteResult> result = docRef.set(message);
//    }

//    @Override
//    public void sendMessage(NewMessageRequest request) {
//        String collectionName = request.getSenderId() + "AND" + request.getRecipientId();
//        Firestore db = FirestoreClient.getFirestore();
//        ApiFuture<QuerySnapshot> myDb = db.collection(collectionName).get();
//        // Check if the collection exists
//        try {
//            List<QueryDocumentSnapshot> documents = myDb.get().getDocuments();
//            if (documents.isEmpty()) {
//                System.out.println("Collection does not exist");
//            } else {
//                System.out.println("Collection exists");
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//        DocumentReference docRef = db.collection(collectionName).document();
//        ChatMessage message = new ChatMessage(request.getSenderId(), request.getRecipientId(), request.getContent());
//        ApiFuture<WriteResult> result = docRef.set(message);
//    }

    @Override
    public boolean isChatBoxExist(String senderId, String recipientId) {
        String collectionName1 = senderId + "AND" + recipientId;
        String collectionName2 = recipientId + "AND" + senderId;

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future1 = db.collection(collectionName1).get();
        ApiFuture<QuerySnapshot> future2 = db.collection(collectionName2).get();

        try {
            List<QueryDocumentSnapshot> documents1 = future1.get().getDocuments();
            List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
            if (!documents1.isEmpty() || !documents2.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void sendMessage(NewMessageRequest request) {
        if (request.getSenderId().equals(request.getRecipientId())) {
            throw new IllegalArgumentException("Sender and recipient cannot be the same!");
        } else {
            String collectionName1 = request.getSenderId() + "AND" + request.getRecipientId();
            String collectionName2 = request.getRecipientId() + "AND" + request.getSenderId();

            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> future1 = db.collection(collectionName1).get();
            ApiFuture<QuerySnapshot> future2 = db.collection(collectionName2).get();

            try {
                List<QueryDocumentSnapshot> documents1 = future1.get().getDocuments();
                List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
                if (!documents1.isEmpty()) {
                    DocumentReference docRef = db.collection(collectionName1).document();
                    ChatMessage message = new ChatMessage(request.getSenderId(), request.getRecipientId(), request.getContent());
                    ApiFuture<WriteResult> result = docRef.set(message);

//                    NewChatBoxRequest newChatBoxRequest = NewChatBoxRequest.builder()
//                            .senderId(UUID.fromString(request.getSenderId()))
//                            .recipientId(UUID.fromString(request.getRecipientId()))
//                            .build();
//                    chatBoxService.createNewChatBox(newChatBoxRequest); // Changed this line
                } else if (!documents2.isEmpty()) {
                    DocumentReference docRef = db.collection(collectionName2).document();
                    ChatMessage message = new ChatMessage(request.getSenderId(), request.getRecipientId(), request.getContent());
                    ApiFuture<WriteResult> result = docRef.set(message);

//                    NewChatBoxRequest newChatBoxRequest = NewChatBoxRequest.builder()
//                            .senderId(UUID.fromString(request.getRecipientId()))
//                            .recipientId(UUID.fromString(request.getSenderId()))
//                            .build();
//                    chatBoxService.createNewChatBox(newChatBoxRequest); // Changed this line
                } else {
                    DocumentReference docRef = db.collection(collectionName1).document();
                    ChatMessage message = new ChatMessage(request.getSenderId(), request.getRecipientId(), request.getContent());
                    ApiFuture<WriteResult> result = docRef.set(message);
                    NewChatBoxRequest newChatBoxRequest = NewChatBoxRequest.builder()
                            .senderId(UUID.fromString(request.getSenderId()))
                            .recipientId(UUID.fromString(request.getRecipientId()))
                            .build();
                    chatBoxService.createNewChatBox(newChatBoxRequest);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}


// ----------------- READ -----------------
//Firestore db = FirestoreClient.getFirestore();
//DocumentReference docRef = db.collection("collectionName").document("documentId");
//ApiFuture<DocumentSnapshot> future = docRef.get();
//DocumentSnapshot document = future.get();
//if (document.exists()) {
//        // Document data may be retrieved with .get(String)
//        System.out.println("Document data: " + document.getData());
//        } else {
//        System.out.println("No such document!");
//}


// ----------------- WRITE -----------------
//Firestore db = FirestoreClient.getFirestore();
//DocumentReference docRef = db.collection("collectionName").document("documentId");
//Map<String, Object> data = new HashMap<>();
//data.put("key", "value");
//ApiFuture<WriteResult> result = docRef.set(data);


// ----------------- EDIT -----------------
//Firestore db = FirestoreClient.getFirestore();
//DocumentReference docRef = db.collection("collectionName").document("documentId");
//Map<String, Object> updates = new HashMap<>();
//updates.put("key", "newValue");
//ApiFuture<WriteResult> result = docRef.update(updates);


// ----------------- DELETE -----------------
//Firestore db = FirestoreClient.getFirestore();
//DocumentReference docRef = db.collection("collectionName").document("documentId");
//ApiFuture<WriteResult> result = docRef.delete();








//@Override
//public void sendMessage(NewMessageRequest request) {
//    String collectionName1 = request.getSenderId() + "AND" + request.getRecipientId();
//    String collectionName2 = request.getRecipientId() + "AND" + request.getSenderId();
//
//    Firestore db = FirestoreClient.getFirestore();
//    ApiFuture<QuerySnapshot> future1 = db.collection(collectionName1).get();
//    ApiFuture<QuerySnapshot> future2 = db.collection(collectionName2).get();
//
//    try {
//        List<QueryDocumentSnapshot> documents1 = future1.get().getDocuments();
//        List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
//        if (!documents1.isEmpty()) {
//            DocumentReference docRef = db.collection(collectionName1).document();
//            ChatMessage message = new ChatMessage(request.getSenderId(), request.getRecipientId(), request.getContent());
//            ApiFuture<WriteResult> result = docRef.set(message);
//            if (isChatBoxExist(request.getSenderId(), request.getRecipientId())) {
//                System.out.println("Chat box exists");
//            } else {
//                NewChatBoxRequest newChatBoxRequest = NewChatBoxRequest.builder()
//                        .senderId(UUID.fromString(request.getSenderId()))
//                        .recipientId(UUID.fromString(request.getRecipientId()))
//                        .build();
//                chatBoxService.createChatBox(newChatBoxRequest);
//            };
//        } else if (!documents2.isEmpty()) {
//            DocumentReference docRef = db.collection(collectionName2).document();
//            ChatMessage message = new ChatMessage(request.getSenderId(), request.getRecipientId(), request.getContent());
//            ApiFuture<WriteResult> result = docRef.set(message);
//            if (isChatBoxExist(request.getSenderId(), request.getRecipientId())) {
//                System.out.println("Chat box exists");
//            } else {
//                NewChatBoxRequest newChatBoxRequest = NewChatBoxRequest.builder()
//                        .senderId(UUID.fromString(request.getSenderId()))
//                        .recipientId(UUID.fromString(request.getRecipientId()))
//                        .build();
//                chatBoxService.createChatBox(newChatBoxRequest);
//            };
//        } else {
//            DocumentReference docRef = db.collection(collectionName1).document();
//            ChatMessage message = new ChatMessage(request.getSenderId(), request.getRecipientId(), request.getContent());
//            ApiFuture<WriteResult> result = docRef.set(message);
//        }
//    } catch (InterruptedException | ExecutionException e) {
//        e.printStackTrace();
//    }
//}