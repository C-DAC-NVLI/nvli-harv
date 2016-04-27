package in.gov.nvli.harvester.restModels;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {

	private String errorMessage;
	private int errorCode;
        private Throwable e;
	
	public ErrorMessage() {
		
	}
		
	public ErrorMessage(String errorMessage, int errorCode,Throwable e) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
                this.e=e;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

    public Throwable getE() {
        return e;
    }

    public void setE(Throwable e) {
        this.e = e;
    }
	
	
}
