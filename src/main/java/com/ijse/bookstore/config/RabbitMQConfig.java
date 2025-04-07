package com.ijse.bookstore.config;
// Define o pacote onde esta classe está inserida (deve corresponder à estrutura de pastas).

// Importações necessárias do Spring AMQP para trabalhar com RabbitMQ
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Indica ao Spring que esta classe é de configuração
public class RabbitMQConfig {

    // Nome da fila para onde as mensagens serão enviadas
    public static final String SHIPPING_QUEUE = "shipping.queue";

    // Nome da exchange (ponto central que distribui mensagens)
    public static final String SHIPPING_EXCHANGE = "shipping.exchange";

    // Routing key usada para encaminhar mensagens da exchange para a fila
    public static final String SHIPPING_ROUTING_KEY = "shipping.routingkey";

    @Bean // Define o bean da fila para que seja criada automaticamente ao arrancar a aplicação
    public Queue shippingQueue() {
        return new Queue(SHIPPING_QUEUE); // Cria uma nova fila com o nome definido acima
    }

    @Bean // Define o bean da exchange (tipo tópico)
    public TopicExchange shippingExchange() {
        return new TopicExchange(SHIPPING_EXCHANGE); // Cria uma exchange com o nome definido acima
    }

    @Bean // Define o bean que faz a ligação (binding) entre a fila e a exchange com a routing key
    public Binding shippingBinding(Queue shippingQueue, TopicExchange shippingExchange) {
        // Liga a fila à exchange usando a routing key definida
        return BindingBuilder
                .bind(shippingQueue) // Fila de destino
                .to(shippingExchange) // Exchange de origem
                .with(SHIPPING_ROUTING_KEY); // Routing key usada na ligação
    }
}
