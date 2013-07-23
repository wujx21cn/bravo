/* com.cutty.bravo.core.utils.SigarUtils.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-11-20 上午10:42:22, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

 */
package com.cutty.bravo.core.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarNotImplementedException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.Who;

/**
 * 
 * <p>
 * <a href="SigarUtils.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SigarUtils {
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 获取CPU个数
	 * 
	 * @return
	 * @throws SigarException
	 */
	private static int getCpuCount(Sigar sigar) throws SigarException {
		return sigar.getCpuInfoList().length;
	}

	/**
	 * 获取CPU的总量（单位：HZ）及CPU的相关信息
	 * CPU的用户使用量、系统使用剩余量、总的剩余量、总的使用占用量等（单位：100%）
	 * 
	 * @throws SigarException
	 */
	public static CpuInfo[] getCpuInfos(Sigar sigar) throws SigarException {
		CpuInfo[] infos = sigar.getCpuInfoList();
		/*
		for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
			CpuInfo info = infos[i];
			System.out.println("mhz=" + info.getMhz());// CPU的总量MHz
			System.out.println("vendor=" + info.getVendor());// 获得CPU的卖主，如：Intel
			System.out.println("model=" + info.getModel());// 获得CPU的类别，如：Celeron
			System.out.println("cache size=" + info.getCacheSize());// 缓冲存储器数量
			System.out.println("User :" + CpuPerc.format(cpu.getUser()));// 用户使用率
			System.out.println("Sys :" + CpuPerc.format(cpu.getSys()));// 系统使用率
			System.out.println("Wait :" + CpuPerc.format(cpu.getWait()));// 当前等待率
			System.out.println("Nice :" + CpuPerc.format(cpu.getNice()));//
			System.out.println("Idle :" + CpuPerc.format(cpu.getIdle()));// 当前空闲率
			System.out.println("Total :" + CpuPerc.format(cpu.getCombined()));// 总的使用率
		}
		*/
		return infos;

	}


	/**
	 * 获取当前内存使用信息,单位为kb
	 * 
	 * @throws SigarException
	 */
	private static Mem getMemoryInfo(Sigar sigar) throws SigarException {
		Mem mem = sigar.getMem();
		/*
		System.out.println("Total = " + mem.getTotal() / 1024L + "K av");// 内存总量
		System.out.println("Used = " + mem.getUsed() / 1024L + "K used");// 当前内存使用量
		System.out.println("Free = " + mem.getFree() / 1024L + "K free");// 当前内存剩余量
		 */
		return mem;
	}

	/**
	 * 获取交换区信息
	 * @param sigar
	 * @return
	 * @throws SigarException
	 */
	private static Swap getSwapInfo(Sigar sigar) throws SigarException {
		Swap swap = sigar.getSwap();
		/*
		System.out.println("Total = " + swap.getTotal() / 1024L + "K av");// 交换区总量
		System.out.println("Used = " + swap.getUsed() / 1024L + "K used");// 当前交换区使用量
		System.out.println("Free = " + swap.getFree() / 1024L + "K free");// 当前交换区剩余量
		*/
		return swap;
	}

	/**
	 * 取到当前操作系统的名称：
	 * @param sigar
	 * @return
	 * @throws UnknownHostException
	 * @throws SigarException
	 */
	public static String getPlatformName(Sigar sigar)  throws UnknownHostException,SigarException{
		String hostname = "";
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			hostname = sigar.getNetInfo().getHostName();
		}
		return hostname;
	}

	/**
	 * 取当前操作系统的信息
	 * @return
	 */
	public static OperatingSystem getOSInfo() {
		OperatingSystem OS = OperatingSystem.getInstance();
		/*
		System.out.println("OS.getArch() = " + OS.getArch());// 操作系统内核类型如： 386、486、586等x86
		System.out.println("OS.getCpuEndian() = " + OS.getCpuEndian());//
		System.out.println("OS.getDataModel() = " + OS.getDataModel());//
		System.out.println("OS.getDescription() = " + OS.getDescription());		// 系统描述
		System.out.println("OS.getMachine() = " + OS.getMachine());//
		System.out.println("OS.getName() = " + OS.getName());	// 操作系统类型
		System.out.println("OS.getPatchLevel() = " + OS.getPatchLevel());//
		System.out.println("OS.getVendor() = " + OS.getVendor());// 操作系统的卖主		
		System.out.println("OS.getVendorCodeName() = " + OS.getVendorCodeName());// 卖主名称		
		System.out.println("OS.getVendorName() = " + OS.getVendorName());// 操作系统名称		
		System.out.println("OS.getVendorVersion() = " + OS.getVendorVersion());// 操作系统卖主类型		
		System.out.println("OS.getVersion() = " + OS.getVersion());// 操作系统的版本号
		*/
		return OS;
	}

	/**
	 * 取当前系统进程表中的用户信息
	 */
	public static Who[] getThreadInfos(Sigar sigar)  throws SigarException {
		Who[] threads = sigar.getWhoList();
		/*
		if (threads != null && threads.length > 0) {
			for (int i = 0; i < threads.length; i++) {
				System.out.println("\n~~~~~~~~~" + String.valueOf(i)
						+ "~~~~~~~~~");
				Who thread = threads[i];
				System.out.println("getDevice() = " + thread.getDevice());
				System.out.println("getHost() = " + thread.getHost());
				System.out.println("getTime() = " + thread.getTime());
				// 当前系统进程表中的用户名
				System.out.println("getUser() = " + thread.getUser());
			}
		}*/
		return threads;
	}

	/**
	 * 取硬盘已有的分区及其详细信息（通过sigar.getFileSystemList()来获得FileSystem列表对象，然后对其进行编历）：
	 * @return
	 * @throws SigarException
	 */
	public static FileSystem[] getFileSystemInfo(Sigar sigar) throws SigarException  {
		FileSystem[] fslist = sigar.getFileSystemList();
		/*
		String dir = System.getProperty("user.home");// 当前用户文件夹路径
		for (int i = 0; i < fslist.length; i++) {
			System.out.println("\n~~~~~~~~~~" + i + "~~~~~~~~~~");
			FileSystem fs = fslist[i];
			// 分区的盘符名称
			System.out.println("fs.getDevName() = " + fs.getDevName());
			// 分区的盘符名称
			System.out.println("fs.getDirName() = " + fs.getDirName());
			System.out.println("fs.getFlags() = " + fs.getFlags());//
			// 文件系统类型，比如 FAT32、NTFS
			System.out.println("fs.getSysTypeName() = " + fs.getSysTypeName());
			// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
			System.out.println("fs.getTypeName() = " + fs.getTypeName());
			// 文件系统类型
			System.out.println("fs.getType() = " + fs.getType());
			FileSystemUsage usage = null;
			try {
				usage = sigar.getFileSystemUsage(fs.getDirName());
			} catch (SigarException e) {
				if (fs.getType() == 2)
					throw e;
				continue;
			}
			switch (fs.getType()) {
			case 0: // TYPE_UNKNOWN ：未知
				break;
			case 1: // TYPE_NONE
				break;
			case 2: // TYPE_LOCAL_DISK : 本地硬盘
				// 文件系统总大小
				System.out.println(" Total = " + usage.getTotal() + "KB");
				// 文件系统剩余大小
				System.out.println(" Free = " + usage.getFree() + "KB");
				// 文件系统可用大小
				System.out.println(" Avail = " + usage.getAvail() + "KB");
				// 文件系统已经使用量
				System.out.println(" Used = " + usage.getUsed() + "KB");
				double usePercent = usage.getUsePercent() * 100D;
				// 文件系统资源的利用率
				System.out.println(" Usage = " + usePercent + "%");
				break;
			case 3:// TYPE_NETWORK ：网络
				break;
			case 4:// TYPE_RAM_DISK ：闪存
				break;
			case 5:// TYPE_CDROM ：光驱
				break;
			case 6:// TYPE_SWAP ：页面交换
				break;
			}
			System.out.println(" DiskReads = " + usage.getDiskReads());
			System.out.println(" DiskWrites = " + usage.getDiskWrites());
		}
		*/
		return fslist;
	}

	/**
	 * 当前机器的正式域名
	 * @param sigar
	 * @return
	 * @throws SigarException
	 */
	public static String getFQDN(Sigar sigar) throws SigarException{
		try {
			return InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			sigar = new Sigar();
			return sigar.getFQDN();
		}
	}

	/**
	 * 取到当前机器的IP地址
	 * @param sigar
	 * @return
	 * @throws SigarException
	 */
	public static String getDefaultIpAddress(Sigar sigar) throws SigarException{
		String address = null;
		try {
			address = InetAddress.getLocalHost().getHostAddress();
			// 没有出现异常而正常当取到的IP时，如果取到的不是网卡循回地址时就返回
			// 否则再通过Sigar工具包中的方法来获取
			if (!NetFlags.LOOPBACK_ADDRESS.equals(address)) {
				return address;
			}
		} catch (UnknownHostException e) {
			address = sigar.getNetInterfaceConfig().getAddress();
		}
		return address;
	}

	/**
	 * 取到当前机器的MAC地址
	 * @param sigar
	 * @return
	 * @throws SigarException
	 */
	public static String getMAC(Sigar sigar) throws SigarException{
		String[] ifaces = sigar.getNetInterfaceList();
		String hwaddr = null;
		for (int i = 0; i < ifaces.length; i++) {
			NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
			if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
					|| (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
					|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
				continue;
			}
			/*
			 * 如果存在多张网卡包括虚拟机的网卡，默认只取第一张网卡的MAC地址，如果要返回所有的网卡（包括物理的和虚拟的）则可以修改方法的返回类型为数组或Collection
			 * ，通过在for循环里取到的多个MAC地址。
			 */
			hwaddr = cfg.getHwaddr();
			break;
		}
		return hwaddr != null ? hwaddr : null;
	}

	/**
	 * 获取网络流量等信息
	 * 
	 * @throws Exception
	 */
	public static void testNetIfList() throws Exception {
		Sigar sigar = new Sigar();
		String ifNames[] = sigar.getNetInterfaceList();
		for (int i = 0; i < ifNames.length; i++) {
			String name = ifNames[i];
			NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
			System.out.println("\nname = " + name);// 网络设备名
			System.out.println("Address = " + ifconfig.getAddress());// IP地址
			System.out.println("Netmask = " + ifconfig.getNetmask());// 子网掩码
			if ((ifconfig.getFlags() & 1L) <= 0L) {
				System.out.println("!IFF_UP...skipping getNetInterfaceStat");
				continue;
			}
			try {
				NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
				System.out.println("RxPackets = " + ifstat.getRxPackets());// 接收的总包裹数
				System.out.println("TxPackets = " + ifstat.getTxPackets());// 发送的总包裹数
				System.out.println("RxBytes = " + ifstat.getRxBytes());// 接收到的总字节数
				System.out.println("TxBytes = " + ifstat.getTxBytes());// 发送的总字节数
				System.out.println("RxErrors = " + ifstat.getRxErrors());// 接收到的错误包数
				System.out.println("TxErrors = " + ifstat.getTxErrors());// 发送数据包时的错误数
				System.out.println("RxDropped = " + ifstat.getRxDropped());// 接收时丢弃的包数
				System.out.println("TxDropped = " + ifstat.getTxDropped());// 发送时丢弃的包数
			} catch (SigarNotImplementedException e) {
			} catch (SigarException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void getEthernetInfo() {
		Sigar sigar = null;
		try {
			sigar = new Sigar();
			String[] ifaces = sigar.getNetInterfaceList();
			for (int i = 0; i < ifaces.length; i++) {
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
				if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
						|| (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
						|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
					continue;
				}
				System.out.println("cfg.getAddress() = " + cfg.getAddress());// IP地址
				System.out
						.println("cfg.getBroadcast() = " + cfg.getBroadcast());// 网关广播地址
				System.out.println("cfg.getHwaddr() = " + cfg.getHwaddr());// 网卡MAC地址
				System.out.println("cfg.getNetmask() = " + cfg.getNetmask());// 子网掩码
				System.out.println("cfg.getDescription() = "
						+ cfg.getDescription());// 网卡描述信息
				System.out.println("cfg.getType() = " + cfg.getType());//
				System.out.println("cfg.getDestination() = "
						+ cfg.getDestination());
				System.out.println("cfg.getFlags() = " + cfg.getFlags());//
				System.out.println("cfg.getMetric() = " + cfg.getMetric());
				System.out.println("cfg.getMtu() = " + cfg.getMtu());
				System.out.println("cfg.getName() = " + cfg.getName());
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println("Error while creating GUID" + e);
		} finally {
			if (sigar != null)
				sigar.close();
		}
	}
}
