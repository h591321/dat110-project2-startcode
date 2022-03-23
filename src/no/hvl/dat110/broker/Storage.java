package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.messages.PublishMsg;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	protected ConcurrentHashMap<String, Set<PublishMsg>> buffer;
	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {

		// TODO: add corresponding client session to the storage
		// See ClientSession class
		ClientSession client = new ClientSession(user, connection);
		clients.put(user, client);
		
	}

	public void removeClientSession(String user) {

		//  disconnet the client (user) 
		// and remove client session for user from the storage
		clients.get(user).disconnect();
		clients.remove(user);
		
	}

	public void createTopic(String topic) {

		//  create topic in the storage
		subscriptions.put(topic, new HashSet<>());
	
	}

	public void deleteTopic(String topic) {

		//  delete topic from the storage

		subscriptions.remove(topic);
	}

	public void addSubscriber(String user, String topic) {

		// add the user as subscriber to the topic
		Set<String> subs = subscriptions.get(topic);
		subs.add(user);
		subscriptions.put(user, subs);
	}

	public void removeSubscriber(String user, String topic) {

		// remove the user as subscriber to the topic
		Set<String> subs = subscriptions.get(topic);
		subs.remove(user);
		subscriptions.put(topic, subs);
	}
	
	public void addBufferUser(String user) {
		buffer.put(user, new HashSet<>());
	}
	
	public void addBufferMsg(PublishMsg msg) {
		Set<PublishMsg> messages = buffer.get(msg.getUser());
		messages.add(msg);
		buffer.put(msg.getUser(), messages);
	}
	public void removeBuffer(String user) {
		buffer.remove(user);
	}
	public Set<PublishMsg> getBuffer(String user){
		return buffer.get(user);
	}
	public boolean hasBufferWaiting(String user) {
		if(buffer.get(user)!=null) {
			if(!buffer.get(user).isEmpty())
				return true;
		}
		return false;
	}
}
