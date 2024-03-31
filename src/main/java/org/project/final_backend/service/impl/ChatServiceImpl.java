package org.project.final_backend.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.project.final_backend.domain.request.chat.NewMessageRequest;
import org.project.final_backend.entity.ChatMessage;
import org.project.final_backend.service.ChatService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ChatServiceImpl implements ChatService {

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


    @Override
    public void sendMessage(NewMessageRequest request) {
        String collectionName = request.getSenderId() + "AND" + request.getRecipientId();
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(collectionName).document();
        ChatMessage message = new ChatMessage(request.getChatId(), request.getSenderId(), request.getRecipientId(), request.getContent());
        ApiFuture<WriteResult> result = docRef.set(message);
    }
}