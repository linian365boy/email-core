/**
* @Author ln
* @Date 2014年7月16日 上午10:41:25    
* @Version V4.0
* @Company 聚成华企科技有限公司
*/
package com.nian.util.email;
public interface MailService {
	/**
	* @Description 发送邮件
	* @param receivers	接收人
	* @param subject	主题
	* @param content	内容
	* @Author ln
	* @Date 2014年7月16日 上午10:43:08
	 */
	public abstract void sendHtmlEmails(String receivers, String subject, String content);
	
}
