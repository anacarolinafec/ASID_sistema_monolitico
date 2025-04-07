package com.ijse.bookstore.messagingRABBIT;

import com.ijse.bookstore.entity.OrderDetails;
import com.ijse.bookstore.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shipping")  // Prefixo para este controlador
public class sendMessage_orderId {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @PostMapping("/{orderId}")
    public ResponseEntity<?> confirmarEnvio(@PathVariable Long orderId) {
        // Verifica se a encomenda existe
        Optional<OrderDetails> order = orderDetailsRepository.findById(orderId);
        if (order.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Encomenda não encontrada");
        }

        // Envia apenas o ID da encomenda para o serviço de shipping
        messageProducer.sendMessage(orderId.toString());

        return ResponseEntity.ok("ID da encomenda enviado para o serviço de shipping.");
    }
}
