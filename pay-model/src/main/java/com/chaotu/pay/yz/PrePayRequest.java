package com.chaotu.pay.yz;

import com.chaotu.pay.po.TYzUserAddress;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrePayRequest {

    private PrePaySource source;

    private Object usePayAsset;

    private PrePayConfig config;

    private List<PrePayItems> items;

    private PrePaySeller seller;

    private PrePayDelivery delivery;

    private PrePayUmp ump;

    private String[] unavailableItems;


    public PrePayRequest(TYzUserAddress address, String kdtSessionId, String clientIp, int goodsId, int skuId, int kdtId, int price){

        this.config = new PrePayConfig();
        this.delivery = new PrePayDelivery();
        this.delivery.setAddress(address);
        this.items = new ArrayList<>(1);
        PrePayItems prePayItems = new PrePayItems();
        prePayItems.setPrice(price);
        prePayItems.setGoodsId(goodsId);
        prePayItems.setKdtId(kdtId);
        prePayItems.setSkuId(skuId);
        items.add(prePayItems);
        this.seller = new PrePaySeller();
        seller.setKdtId(kdtId);
        this.ump = new PrePayUmp();
        List<PrePayActivites> activites = new ArrayList<>(1);
        PrePayActivites activite = new PrePayActivites();
        activite.setKdtId(kdtId);
        activite.setGoodsId(goodsId);
        activites.add(activite);
        activite.setSkuId(skuId);
        ump.setActivities(activites);
        this.source = new PrePaySource();
        source.setKdtSessionId(kdtSessionId);
        source.setClientIp(clientIp);
        PreParyItemSources itemSources = new PreParyItemSources();
        itemSources.setGoodsId(goodsId);
        itemSources.setSkuId(skuId);
        List<PreParyItemSources> itemSourcesList = new ArrayList<>(1);
        itemSourcesList.add(itemSources);
        source.setItemSources(itemSourcesList);
        this.unavailableItems = new String[0];
        this.usePayAsset = new Object();
    }
}
