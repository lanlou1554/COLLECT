package com.seiii.collect.model.po.user.worker;

import com.seiii.collect.model.dto.user.worker.WorkerContextDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class WorkerContext implements Serializable {
    @ApiModelProperty(value = "", name = "workerid")
    private Integer workerid;

    @ApiModelProperty(value = "", name = "devicetype")
    private String devicetype;

    @ApiModelProperty(value = "", name = "osinfo")
    private String osinfo;

    @ApiModelProperty(value = "", name = "ramsize")
    private String ramsize;

    @ApiModelProperty(value = "", name = "netenv")
    private String netenv;

    private static final long serialVersionUID = 1L;

    /**
     * @return workerId
     */
    public Integer getWorkerid() {
        return workerid;
    }

    /**
     * @param workerid
     */
    public void setWorkerid(Integer workerid) {
        this.workerid = workerid;
    }

    /**
     * @return deviceType
     */
    public String getDevicetype() {
        return devicetype;
    }

    /**
     * @param devicetype
     */
    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype == null ? null : devicetype.trim();
    }

    /**
     * @return osInfo
     */
    public String getOsinfo() {
        return osinfo;
    }

    /**
     * @param osinfo
     */
    public void setOsinfo(String osinfo) {
        this.osinfo = osinfo == null ? null : osinfo.trim();
    }

    /**
     * @return ramSize
     */
    public String getRamsize() {
        return ramsize;
    }

    /**
     * @param ramsize
     */
    public void setRamsize(String ramsize) {
        this.ramsize = ramsize == null ? null : ramsize.trim();
    }

    /**
     * @return netEnv
     */
    public String getNetenv() {
        return netenv;
    }

    /**
     * @param netenv
     */
    public void setNetenv(String netenv) {
        this.netenv = netenv == null ? null : netenv.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", workerid=").append(workerid);
        sb.append(", devicetype=").append(devicetype);
        sb.append(", osinfo=").append(osinfo);
        sb.append(", ramsize=").append(ramsize);
        sb.append(", netenv=").append(netenv);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public WorkerContext(Integer userId, WorkerContextDTO workerContextDTO){
        workerid=userId;
        devicetype=workerContextDTO.getDeviceType();
        osinfo=workerContextDTO.getOsInfo();
        ramsize=workerContextDTO.getRamSize();
        netenv=workerContextDTO.getNetEnv();
    }
}