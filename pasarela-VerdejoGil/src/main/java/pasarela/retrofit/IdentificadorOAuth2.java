package pasarela.retrofit;

public class IdentificadorOAuth2 {
	
	private String oAuth2Id;

	public IdentificadorOAuth2(String oAuth2Id) {
		super();
		this.oAuth2Id = oAuth2Id;
	}
	
	public IdentificadorOAuth2() {

	}

	public String getoAuth2Id() {
		return oAuth2Id;
	}

	public void setoAuth2Id(String oAuth2Id) {
		this.oAuth2Id = oAuth2Id;
	}
}
