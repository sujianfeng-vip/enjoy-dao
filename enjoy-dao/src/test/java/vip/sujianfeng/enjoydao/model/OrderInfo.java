package vip.sujianfeng.enjoydao.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vip.sujianfeng.enjoydao.annotations.*;
import vip.sujianfeng.enjoydao.enums.TbDefineFieldType;

/**
 * @author Xiao-Bai
 * @date 2022/12/7 0007 9:34
 */
@ApiModel("套系订单PO")
@TbTableUuid(
        table = "order_info"
)
public class OrderInfo extends AbstractBizModel {

        @ApiModelProperty("入册张数")
        @TbFieldInt(
                tableField = "add_album_num",
                label = "入册张数"
        )
        
        private Integer addAlbumNum;
        @ApiModelProperty("增加的优惠券价格")
        @TbFieldInt(
                tableField = "add_coupon_amount",
                label = "增加的优惠券价格"
        )
        
        private Integer addCouponAmount;
        @ApiModelProperty("精修张数")
        @TbFieldInt(
                tableField = "add_ps_num",
                label = "精修张数"
        )
        
        private Integer addPsNum;
        @ApiModelProperty("入册数量")
        @TbFieldInt(
                tableField = "album_num",
                label = "入册数量"
        )
        
        private Integer albumNum;
        @ApiModelProperty("退单原因")
        @TbFieldString(
                tableField = "back_reason",
                label = "退单原因",
                length = 500
        )
        
        private String backReason;
        @ApiModelProperty("城市Id")
        @TbFieldString(
                tableField = "city_id",
                label = "城市Id",
                length = 50
        )
        
        private String cityId;
        @ApiModelProperty("客资Id")
        @TbFieldString(
                tableField = "client_id",
                label = "客资Id",
                length = 50
        )
        
        private String clientId;
        @ApiModelProperty("企业id")
        @TbFieldString(
                tableField = "corp_id",
                label = "企业id",
                length = 50
        )
        
        private String corpId;
        @ApiModelProperty("欠付金额")
        @TbFieldInt(
                tableField = "debt_amount",
                label = "欠付金额"
        )
        
        private Integer debtAmount;
        @ApiModelProperty("送货地址")
        @TbFieldString(
                tableField = "delivery_address",
                label = "送货地址",
                length = 500
        )
        
        private String deliveryAddress;
        @ApiModelProperty("已付金额")
        @TbFieldInt(
                tableField = "have_amount",
                label = "已付金额"
        )
        
        private Integer haveAmount;
        @ApiModelProperty("内景套数")
        @TbFieldInt(
                tableField = "inside_clothes_num",
                label = "内景套数"
        )
        
        private Integer insideClothesNum;
        @ApiModelProperty("套系Id")
        @TbFieldString(
                tableField = "meal_id",
                label = "套系Id",
                length = 50
        )
        
        private String mealId;
        @ApiModelProperty("套系数据")
        @TbField(
                fieldType = TbDefineFieldType.ftText,
                tableField = "meal_json",
                label = "套系数据"
        )
        
        private String mealJson;
        @ApiModelProperty("底片数量")
        @TbFieldInt(
                tableField = "negative_num",
                label = "底片数量"
        )
        
        private Integer negativeNum;
        @ApiModelProperty("订单备注")
        @TbField(
                fieldType = TbDefineFieldType.ftText,
                tableField = "order_memo",
                label = "订单备注"
        )
        
        private String orderMemo;
        @ApiModelProperty("订单编号")
        @TbFieldString(
                tableField = "order_num",
                label = "订单编号",
                length = 50
        )
        
        private String orderNum;
        @ApiModelProperty("0-门市开单，1-在线开单")
        @TbFieldInt(
                tableField = "order_source",
                label = "0-门市开单，1-在线开单"
        )
        
        private Integer orderSource;
        @ApiModelProperty("订单状态（0-正常, 1-退单,2-作废）")
        @TbFieldInt(
                tableField = "order_status",
                label = "订单状态（0-正常, 1-退单,2-作废）"
        )
        
        private Integer orderStatus;
        @ApiModelProperty("订单时间")
        @TbFieldInt(
                tableField = "order_time",
                label = "订单时间"
        )
        
        private Integer orderTime;
        @ApiModelProperty("订单类型： 0-婚纱照，1-微视频，2-写真，3-儿童，4-全家福")
        @TbFieldInt(
                tableField = "order_type",
                label = "订单类型： 0-婚纱照，1-微视频，2-写真，3-儿童，4-全家福"
        )
        
        private Integer orderType;
        @ApiModelProperty("原价（分）")
        @TbFieldInt(
                tableField = "original_price",
                label = "原价（分）"
        )
        
        private Integer originalPrice;
        @ApiModelProperty("外景套数")
        @TbFieldInt(
                tableField = "outside_clothes_num",
                label = "外景套数"
        )
        
        private Integer outsideClothesNum;
        @ApiModelProperty("预收款记录id")
        @TbFieldString(
                tableField = "pre_pay_Log_id",
                label = "预收款记录id",
                length = 50
        )
        
        private String prePayLogId;
        @ApiModelProperty("精修数量")
        @TbFieldInt(
                tableField = "ps_num",
                label = "精修数量"
        )
        
        private Integer psNum;
        @ApiModelProperty("应付金额")
        @TbFieldInt(
                tableField = "receivable_amount",
                label = "应付金额"
        )
        
        private Integer receivableAmount;
        @ApiModelProperty("优惠价（分）")
        @TbFieldInt(
                tableField = "sale_price",
                label = "优惠价（分）"
        )
        
        private Integer salePrice;
        @ApiModelProperty("服务门店id")
        @TbFieldString(
                tableField = "service_shop_id",
                label = "服务门店id",
                length = 50
        )
        
        private String serviceShopId;
        @ApiModelProperty("拍摄时间")
        @TbFieldInt(
                tableField = "shoot_time",
                label = "拍摄时间"
        )
        
        private Integer shootTime;
        @ApiModelProperty("销售门店id")
        @TbFieldString(
                tableField = "shop_id",
                label = "销售门店id",
                length = 50
        )
        
        private String shopId;
        @ApiModelProperty("服装套数")
        @TbFieldInt(
                tableField = "total_clothes_num",
                label = "服装套数"
        )
        
        private Integer totalClothesNum;
        @ApiModelProperty("订单总成本")
        @TbFieldInt(
                tableField = "total_cost",
                label = "订单总成本"
        )
        
        private Integer totalCost;

        @TbRelationField(joinTable = "order_log", joinTableAlias = "lo", joinCondition = "lo.order_num = a.order_num", srcField = "lo.operator_name")
        private String opName;

    public Integer getAddAlbumNum() {
        return addAlbumNum;
    }

    public void setAddAlbumNum(Integer addAlbumNum) {
        this.addAlbumNum = addAlbumNum;
    }

    public Integer getAddCouponAmount() {
        return addCouponAmount;
    }

    public void setAddCouponAmount(Integer addCouponAmount) {
        this.addCouponAmount = addCouponAmount;
    }

    public Integer getAddPsNum() {
        return addPsNum;
    }

    public void setAddPsNum(Integer addPsNum) {
        this.addPsNum = addPsNum;
    }

    public Integer getAlbumNum() {
        return albumNum;
    }

    public void setAlbumNum(Integer albumNum) {
        this.albumNum = albumNum;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public Integer getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(Integer debtAmount) {
        this.debtAmount = debtAmount;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getHaveAmount() {
        return haveAmount;
    }

    public void setHaveAmount(Integer haveAmount) {
        this.haveAmount = haveAmount;
    }

    public Integer getInsideClothesNum() {
        return insideClothesNum;
    }

    public void setInsideClothesNum(Integer insideClothesNum) {
        this.insideClothesNum = insideClothesNum;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getMealJson() {
        return mealJson;
    }

    public void setMealJson(String mealJson) {
        this.mealJson = mealJson;
    }

    public Integer getNegativeNum() {
        return negativeNum;
    }

    public void setNegativeNum(Integer negativeNum) {
        this.negativeNum = negativeNum;
    }

    public String getOrderMemo() {
        return orderMemo;
    }

    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Integer orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getOutsideClothesNum() {
        return outsideClothesNum;
    }

    public void setOutsideClothesNum(Integer outsideClothesNum) {
        this.outsideClothesNum = outsideClothesNum;
    }

    public String getPrePayLogId() {
        return prePayLogId;
    }

    public void setPrePayLogId(String prePayLogId) {
        this.prePayLogId = prePayLogId;
    }

    public Integer getPsNum() {
        return psNum;
    }

    public void setPsNum(Integer psNum) {
        this.psNum = psNum;
    }

    public Integer getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(Integer receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public String getServiceShopId() {
        return serviceShopId;
    }

    public void setServiceShopId(String serviceShopId) {
        this.serviceShopId = serviceShopId;
    }

    public Integer getShootTime() {
        return shootTime;
    }

    public void setShootTime(Integer shootTime) {
        this.shootTime = shootTime;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getTotalClothesNum() {
        return totalClothesNum;
    }

    public void setTotalClothesNum(Integer totalClothesNum) {
        this.totalClothesNum = totalClothesNum;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }
}
