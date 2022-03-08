public enum  Providers {
    RSA("RSA"),
    RSA_OFB_PKCS1Padding("RSA/OFB/PKCS1Padding"),
    RSA_ECB_OAEPWithSHA_1AndMGF1Padding("RSA/ECB/OAEPWithSHA-1AndMGF1Padding")
    ;

    private String url;

    Providers(String envUrl) {
        this.url = envUrl;
    }

    public String getUrl() {
        return url;
    }

}
