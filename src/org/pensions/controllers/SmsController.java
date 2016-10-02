package org.pensions.controllers;

import java.io.IOException;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.pensions.beans.Sms;

public class SmsController {
	private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();;
	private static final String SYSTEM_ID = "pendpt12";
	private static final String PASSWORD = "123";
	private static final String SERVER_IP = "10.58.160.8";
	private static final int PORT = 2775;

	public boolean sendSms(Sms sms) {
		boolean result = false;
		SMPPSession session = new SMPPSession();

		try {
			//BindParameter bindParameter = new BindParameter(BindType.BIND_TX, SYSTEM_ID,PASSWORD, "cp", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null);
			BindParameter bindParameter = new BindParameter(BindType.BIND_TX, SYSTEM_ID,PASSWORD,"SSMPP", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null);

			session.connectAndBind(SERVER_IP, PORT, bindParameter);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new Exception(e.getMessage());
		}
		//Alfanumeric
		try {
			//String messageId = session.submitShortMessage("CMT", TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, "1616", TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, "+94783448970", new ESMClass(), (byte)0, (byte)1,  timeFormatter.format(new Date()), null, new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT), (byte)0, new GeneralDataCoding(false, true, MessageClass.CLASS1, Alphabet.ALPHA_DEFAULT), (byte)0, "jSMPP simplify SMPP on Java platform".getBytes());
			String messageId = session.submitShortMessage("CP", TypeOfNumber.ALPHANUMERIC, NumberingPlanIndicator.UNKNOWN, "PensionsDPT", TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, sms.getTo(), new ESMClass(), (byte)0, (byte)1,  timeFormatter.format(new java.util.Date()), null, new RegisteredDelivery(SMSCDeliveryReceipt.SUCCESS_FAILURE), (byte)0, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte)0, sms.getMessage().getBytes());

			System.out.println("Message submitted, message_id is " + messageId);
			result = true;
		} catch (PDUException e) {
			// Invalid PDU parameter
			System.err.println("Invalid PDU parameter");
			e.printStackTrace();
			result = false;
		} catch (ResponseTimeoutException e) {
			// Response timeout
			System.err.println("Response timeout");
			e.printStackTrace();
			result = false;
		} catch (InvalidResponseException e) {
			// Invalid response
			System.err.println("Receive invalid respose");
			result = false;
			e.printStackTrace();
		} catch (NegativeResponseException e) {
			// Receiving negative response (non-zero command_status)
			System.err.println("Receive negative response");
			e.printStackTrace();
			result = false;
		} catch (IOException e) {
			System.err.println("IO error occur");
			e.printStackTrace();	
			result = false;
		}
		finally {
			session.unbindAndClose();
		}

		return result;
	}
}
