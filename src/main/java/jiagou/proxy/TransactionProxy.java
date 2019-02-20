package jiagou.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jiagou.annotation.Transaction;
import jiagou.helper.DataBaseHelper;

public class TransactionProxy implements Proxy {
	private final Logger log = LoggerFactory.getLogger(TransactionProxy.class);

	private static ThreadLocal<Boolean> flagHolder = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return false;
		}
	};

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		Method targetMethod = proxyChain.getTargetMethod();
		Boolean flag = flagHolder.get();
		if (flag && targetMethod.isAnnotationPresent(Transaction.class)) {
			flagHolder.set(true);
			try {
				DataBaseHelper.beginTransaction();
				log.debug("begin transaction");
				result = proxyChain.doProxyChain();
				DataBaseHelper.commitTransaction();
				log.debug("commit transaction");
			} catch (Exception e) {
				DataBaseHelper.rollbackTransaction();
				log.debug("rollback transaction");
			} finally {
				flagHolder.remove();
			}
		} else {
			result = proxyChain.doProxyChain();
		}
		return result;
	}

}
