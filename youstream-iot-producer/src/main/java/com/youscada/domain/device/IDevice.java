package com.youscada.domain.device;

import com.youscada.domain.ys.YSPacket;

import java.util.List;

/**
 * Created by lorenzoaddazi on 06/12/16.
 */
public interface IDevice {

    List<YSPacket> generateData() throws InterruptedException;

}
