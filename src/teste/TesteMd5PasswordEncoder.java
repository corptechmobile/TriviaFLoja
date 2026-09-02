package teste;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class TesteMd5PasswordEncoder {
	public static void main(String[] args) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		System.out.println(encoder.encodePassword("123456", null));
	}
}
