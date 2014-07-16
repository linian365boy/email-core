/**
* @Author ln
* @Date 2014年7月16日 上午10:45:22    
* @Version V4.0
* @Company 聚成华企科技有限公司
*/
package com.nian.util.email;

import java.io.IOException;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component("mailService")
public class MailServiceImpl implements MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	@Resource JavaMailSender mailSender;// 注入Spring封装的javamail，Spring的xml中已让框架装配
	@Resource TaskExecutor taskExecutor;// 注入Spring封装的异步执行器

	/*
	 * (non-Javadoc) 发送邮件的具体实现, 目前是异步发送
	 * 
	 * @see
	 * com.haohui.b2b.service.mail.MailService#sendHtmlEmails(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void sendHtmlEmails(String receivers, String subject, String content) {
		this.sendMailByAsynchronousMode(receivers, subject, content);
	}

	/**
	 * 异步发送
	 * 
	 * @see com.zhangjihao.service.MailService#sendMailByAsynchronousMode(com.zhangjihao.bean.Email)
	 */
	public void sendMailByAsynchronousMode(final String receivers, final String subject, final String content) {
		if (logger.isDebugEnabled()) {
			logger.debug("当前邮件采取异步发送..");
		}
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendMailBySynchronizationMode(receivers, subject, content);
					logger.info("邮件发送耗时任务完成");
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}

	/**
	 * 同步发送
	 * 
	 * @throws IOException
	 * @see com.zhangjihao.service.MailServiceMode#sendMail(com.zhangjihao.bean.Email)
	 */

	public void sendMailBySynchronizationMode(String receivers, String subject, String content) throws Exception {
		if (receivers == null) {
			throw new IllegalArgumentException("收件人不能为空");
		}
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "utf-8");

		try {
			receivers = receivers.replaceAll("\\;", ",");
			// 设置收件人，寄件人
			InternetAddress[] toAddress = InternetAddress.parse(receivers);
			mailMessage.setRecipients(Message.RecipientType.TO, toAddress); // 发送给多个账号
			messageHelper.setFrom("1192137134@qq.com"); // 发件人
			messageHelper.setSubject(subject); // 主题
			// true 表示启动HTML格式的邮件
			messageHelper.setText(content, true); // 邮件内容，注意加参数true，表示启用html格式

			// 发送邮件
			mailSender.send(mailMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 或者直接使用 spring 3.0 的异步框架 只需使用 @Async 注解即可。 
	 * 需要激活 <!-- 注解异步任务驱动 -->
	 * <task:annotation-driven/>
	 * 详细配置请见： spring-mail.xml
	 */
	@Async
	public void sendAsync() {
		System.out.println("###### 或者直接采用 spring 3.0 的异步任务注解, 这里的代码直接会用异步线程来运行 #######");
	}
	
}
