package com.chaotu.pay.yz;

import lombok.Data;

import java.util.List;

@Data
public class PrePaySource {
    private String bookKey = "c4e07961-cde5-4dce-b87e-457c375d1797";
    private String clientIp = "1.80.93.47";
    private boolean fromThirdApp = false;
    private List<PreParyItemSources> itemSources;
    private String kdtSessionId;
    private boolean needAppRedirect = false;
    private String orderFrom = "";
    private int orderType = 0;
    private String platform = "mobile";
    private String salesman = "";
    private String userAgent = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Mobile Safari/537.36";
    private String bizPlatform = "";
}
