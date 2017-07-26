/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.openmessaging;

import io.openmessaging.exception.OMSResourceNotExistException;
import io.openmessaging.routing.Operator;
import io.openmessaging.routing.Routing;
import java.util.List;

/**
 * The {@code ResourceManager} is responsible for providing a unified interface of resource management,
 * allows the user to manage the topic, queue, namespace resources.
 * <p>
 * Create, fetch, update and destroy are the four basic functions of {@code ResourceManager}.
 * <p>
 * And the {@code ResourceManager} also supports fetch and update resource attributes dynamically.
 * <p>
 * The attributes of consumer and producer also are treated as {@code Resource}. {@code ResourceManager}
 * allows the user to fetch producer and consumer list in a specified topic or queue,
 * and update their resource attributes dynamically.
 * <p>
 * {@link MessagingAccessPoint#getResourceManager()} is the unique method to obtain a {@code ResourceManager}
 * instance, any changes made by this instance will reflect to the message-oriented middleware (MOM) or
 * other product behind the {@code MessagingAccessPoint}.
 *
 * @author vintagewang@apache.org
 * @author yukon@apache.org
 *
 * @version OMS 1.0
 * @since OMS 1.0
 */
public interface ResourceManager extends ServiceLifecycle {
    /**
     * Creates a {@code Namespace} resource for the specified {@code MessagingAccessPoint} with some preset attributes,
     * updates if it already exists.
     * <p>
     * Note that this method will simply create the physical Namespace in the specified {@code MessagingAccessPoint}.
     *
     * @param nsName the namespace name
     * @param properties the preset attributes
     */
    void createAndUpdateNamespace(String nsName, KeyValue properties);

    /**
     * Creates a {@code Topic} resource for the specified {@code MessagingAccessPoint} with some preset attributes,
     * updates if it already exists.
     * <p>
     * Note that this method will simply create the physical topic in the specified {@code MessagingAccessPoint}.
     *
     * @param topicName the topic name
     * @param properties the preset attributes
     */
    void createAndUpdateTopic(String topicName, KeyValue properties);

    /**
     * Creates a {@code Queue} resource for the specified {@code MessagingAccessPoint} with some preset attributes,
     * updates if it already exists.
     * <p>
     * Note that this method will simply create the physical queue in the specified {@code MessagingAccessPoint}.
     *
     * @param queueName the queue name
     * @param properties the preset attributes
     */
    void createAndUpdateQueue(String queueName, KeyValue properties);

    /**
     * Creates a {@code Routing} resource for the the specified {@code MessagingAccessPoint},
     * updates if it already exits.
     *
     * @param routingName the routing name
     * @param properties the preset attributes, includes
     * {@link OMSBuiltinKeys#SRC_TOPIC} and {@link OMSBuiltinKeys#DST_QUEUE}
     * @return the created routing instance
     */
    Routing createAndUpdateRouting(String routingName, KeyValue properties);

    /**
     * Creates a {@code Operator} resource for the the specified {@code MessagingAccessPoint},
     * updates if it already exits.
     *
     * @param operatorName the operator name
     * @param expression the expression of the operator
     * @param properties the preset attributes
     * @return the created operator instance
     */
    Operator createAndUpdateOperator(String operatorName, String expression, KeyValue properties);

    /**
     * Destroys a physical namespace in the specified {@code MessagingAccessPoint}.
     * <p>
     * All this namespace related physical resources may be deleted immediately.
     * @param nsName the namespace to be destroyed
     */
    void destroyNamespace(String nsName);

    /**
     * Destroys a physical topic in the specified {@code MessagingAccessPoint}.
     * <p>
     * All this topic related physical resources may be deleted immediately.
     * @param topicName the topic to be destroyed
     */
    void destroyTopic(String topicName);

    /**
     * Destroys a physical queue in the specified {@code MessagingAccessPoint}.
     * <p>
     * All this queue related physical resources may be deleted immediately.
     * @param queueName the queue to be destroyed
     */
    void destroyQueue(String queueName);

    /**
     * Destroys a physical {@code Routing} in the specified {@code MessagingAccessPoint}.
     *
     * @param routingName the routing to be destroyed
     */
    void destroyRouting(String routingName);

    /**
     * Fetches the {@code Routing} object by routing name.
     *
     * @param routingName the routing name
     * @return the {@code Routing} object
     */
    Routing getRouting(String routingName);

    /**
     * Fetches the {@code Operator} object by operator name.
     *
     * @param operatorName the operator name
     * @return the {@code Operator} object
     */
    Operator getOperator(String operatorName);

    /**
     * Fetches the resource attributes of a specified namespace.
     *
     * @param nsName the namespace name
     * @return the attributes of this specified namespace
     * @throws OMSResourceNotExistException if the specified namespace is not exists
     */
    KeyValue getNamespaceProperties(String nsName) throws OMSResourceNotExistException;

    /**
     * Fetches the resource attributes of a specified topic.
     *
     * @param topicName the topic name
     * @return the attributes of this specified topic
     * @throws OMSResourceNotExistException if the specified topic is not exists
     */
    KeyValue getTopicProperties(String topicName) throws OMSResourceNotExistException;

    /**
     * Fetches the resource attributes of a specified queue.
     *
     * @param queueName the queue name
     * @return the attributes of this specified queue
     * @throws OMSResourceNotExistException if the specified queue is not exists
     */
    KeyValue getQueueProperties(String queueName) throws OMSResourceNotExistException;

    /**
     * Each consumer has a unique id, this method is to fetch the consumer id list in a specified queue.
     *
     * @param queueName the queue name
     * @return the consumer id list in this queue
     * @throws OMSResourceNotExistException if the specified queue is not exists
     */
    List<String> consumerIdListInQueue(String queueName) throws OMSResourceNotExistException;

    /**
     * Returns the attributes of the specified consumer instance with the given consumer id.
     * If no such consumer id exists, {@code OMSResourceNotExistException} will be thrown.
     *
     * @param consumerId the unique consumer id for a consumer instance
     * @return the attributes of the matching consumer instance
     * @throws OMSResourceNotExistException if the specified consumer is not exists
     */
    KeyValue getConsumerProperties(String consumerId) throws OMSResourceNotExistException;

    /**
     * Sets the consumer attributes to the specified consumer instance.
     * <p>
     * The new {@code KeyValue} becomes the current set of consumer attributes, and the {@link
     * ResourceManager#getConsumerProperties(String)} will observe this change soon. If the argument is null, then the
     * current set of consumer attributes will stay the same.
     *
     * @param consumerId the specified consumer id
     * @param properties the new consumer attributes
     * @throws OMSResourceNotExistException if the specified consumer is not exists
     */
    void setConsumerProperties(String consumerId, KeyValue properties) throws OMSResourceNotExistException;

    /**
     * Each producer has a unique id, this method is to fetch the producer id list in a specified queue.
     *
     * @param queueName the queue name
     * @return the producer id list in this queue
     * @throws OMSResourceNotExistException if the specified queue is not exists
     */
    List<String> producerIdListInQueue(String queueName) throws OMSResourceNotExistException;

    /**
     * Each producer has a unique id, this method is to fetch the producer id list in a specified topic.
     *
     * @param topicName the topic name
     * @return the producer id list in this topic
     * @throws OMSResourceNotExistException if the specified topic is not exists
     */
    List<String> producerIdListInTopic(String topicName) throws OMSResourceNotExistException;

    /**
     * Returns the attributes of the specified producer instance with the given producer id.
     * If no such producer id exists, {@code OMSResourceNotExistException} will be thrown.
     *
     * @param producerId The unique consumer id for a producer instance
     * @return the attributes of the matching producer instance
     * @throws OMSResourceNotExistException if the specified producer is not exists
     */
    KeyValue getProducerProperties(String producerId) throws OMSResourceNotExistException;

    /**
     * Sets the producer attributes to the specified producer instance.
     * <p>
     * The new {@code KeyValue} becomes the current set of producer attributes, and the {@link
     * ResourceManager#getProducerProperties(String)} will observe this change soon. If the argument is null, then the
     * current set of producer attributes will stay the same.
     *
     * @param producerId the specified producer id
     * @param properties the new producer attributes
     * @throws OMSResourceNotExistException if the specified producer is not exists
     */
    void setProducerProperties(String producerId, KeyValue properties) throws OMSResourceNotExistException;

    /**
     * Updates the {@code MessageHeaderBuiltinKeys} of specified message, which may be stored by MOM server already.
     * <p>
     * Only below message sysHeaders allow to be updated:
     * <ul>
     * <li> {@link MessageHeaderBuiltinKeys#Priority}
     * <li> {@link MessageHeaderBuiltinKeys#ScheduleExpression}
     * <li> {@link MessageHeaderBuiltinKeys#StartTime}
     * <li> {@link MessageHeaderBuiltinKeys#StopTime}
     * <li> {@link MessageHeaderBuiltinKeys#Timeout}
     * </ul>
     *
     * @param messageId the message to be updated
     * @param headers the message sysHeaders to be updated
     */
    void updateMessage(String messageId, KeyValue headers);
}
