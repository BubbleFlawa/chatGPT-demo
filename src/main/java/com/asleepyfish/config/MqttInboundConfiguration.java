package com.asleepyfish.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * MQTT消费端
 *
 */
@Configuration
@IntegrationComponentScan
public class MqttInboundConfiguration {

    @Autowired
    private MqttConfiguration mqttProperties;


    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        String[] array = mqttProperties.getUrl().split(",");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(array);
        options.setUserName(mqttProperties.getUsername());
        options.setPassword(mqttProperties.getPassword().toCharArray());
        options.setKeepAliveInterval(2);

        //接受离线消息
        options.setCleanSession(false);
        factory.setConnectionOptions(options);
        return factory;
    }

    //配置client,监听的topic
    @Bean
    public MessageProducer inbound() {
        String[] inboundTopics = mqttProperties.getTopics().split(",");
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                mqttProperties.getClientId()+"_inbound",mqttClientFactory(), inboundTopics);  //对inboundTopics主题进行监听
        adapter.setCompletionTimeout(5000);
        adapter.setQos(1);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }




    //通过通道获取数据
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")  //异步处理
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
//				System.out.println("message:"+message);
                System.out.println("----------------------");
                System.out.println("message:"+message.getPayload());
                System.out.println("PacketId:"+message.getHeaders().getId());
                System.out.println("Qos:"+message.getHeaders().get(MqttHeaders.QOS));

                String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
                System.out.println("topic:"+topic);
            }
        };
    }
}