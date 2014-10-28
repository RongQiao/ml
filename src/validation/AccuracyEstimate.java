package validation;

public class AccuracyEstimate {
	private double e;
	private double V;
	private double sigma;
	public double getE() {
		return e;
	}
	public void setE(double e) {
		this.e = e;
	}
	public double getV() {
		return V;
	}
	public void setV(double v) {
		V = v;
	}
	public double getSigma() {
		return sigma;
	}
	public void setSigma(double delta) {
		this.sigma = delta;
	}
}
