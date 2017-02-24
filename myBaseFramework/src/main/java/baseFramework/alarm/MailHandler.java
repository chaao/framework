package baseFramework.alarm;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.google.common.base.Preconditions;

/**
 * @author chao.li
 * @date 2016年10月12日
 */
public class MailHandler implements InitializingBean {

	private String username;
	private JavaMailSender mailSender;

	@Override
	public void afterPropertiesSet() throws Exception {
		Preconditions.checkNotNull(this.username, "monitor.email.username is required!");
	}

	public void doSend(AlarmMessage msg, List<String> mailAddress) throws Exception {
		SimpleMailMessage mail = new SimpleMailMessage(); // 只发送纯文本
		mail.setFrom(username);
		mail.setSubject(msg.getTitle());// 主题
		mail.setText(msg.getMessage());// 邮件内容
		
		if (!mailAddress.isEmpty()) {
			mail.setTo(mailAddress.toArray(new String[mailAddress.size()]));
			doSendMail(mail);
		}
	}

	private void doSendMail(SimpleMailMessage mail) {
		if (mailSender instanceof JavaMailSenderImpl) {
			JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl) mailSender;
			if (StringUtils.isNotEmpty(mailSenderImpl.getUsername())
					&& StringUtils.isNotEmpty(mailSenderImpl.getPassword())) {
				// 正确设置了账户/密码，才尝试发送邮件
				mailSender.send(mail);
			}
		}
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
