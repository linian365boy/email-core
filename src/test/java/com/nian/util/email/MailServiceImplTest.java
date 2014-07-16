/**
* @Author ln
* @Date 2014年7月16日 上午10:50:08    
* @Version V4.0
* @Company 聚成华企科技有限公司
*/
package com.nian.util.email;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:springContenxt.xml"})
public class MailServiceImplTest {
	
	private MailService mailService;
	
	@Test
	public void testSendEmails(){
		System.out.println("===============发送开始==================");
		this.mailService.sendHtmlEmails("1192137134@qq.com,lily365girl@163.com,linian365boy@sina.com", "SpringMail 异步发送邮件测试", "共同学习共同进步!!! 邮件发送很耗时，还需异步发送啊啊啊啊");
		System.out.println("===============发送结束==================");
	}
	

	public MailService getMailService() {
		return mailService;
	}
	@Resource(name="mailService")
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
}
