package net.qiyuesuo.rpc.thrift;

import net.qiyuesuo.rpc.config.ServerConfig;

public class ThriftServerConfig extends ServerConfig {

	private static final long serialVersionUID = 1L;

	/** thrift配置 */
	private Thrift thrift = new Thrift();

	public Thrift getThrift() {
		return thrift;
	}

	public void setThrift(Thrift thrift) {
		this.thrift = thrift;
	}

	public static class Thrift {
		
		private String protocol = "binary";
		private int selectorThreads = 10;
		private int workerThreads = 10;

		public String getProtocol() {
			return protocol;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public int getSelectorThreads() {
			return selectorThreads;
		}

		public void setSelectorThreads(int selectorThreads) {
			this.selectorThreads = selectorThreads;
		}

		public int getWorkerThreads() {
			return workerThreads;
		}

		public void setWorkerThreads(int workerThreads) {
			this.workerThreads = workerThreads;
		}
	}

}
