import org.jboss.netty.bootstrap {ServerBootstrap, Bootstrap}
import org.jboss.netty.channel.socket.nio {NioServerSocketChannelFactory}
import java.net {InetSocketAddress}
import java.util.concurrent {Executors{newCachedThreadPool}}


//class Httpserver() {
	variable Integer port := 9000;

	shared void run() {
		String[] args = process.arguments;
		if ((nonempty args) && args.size == 1) {
			Integer p = parseInteger(args.first ? "-1") ? -1;
			if (p != -1) {
				port := p;
			}
			print("INFO: Starting server on port: " + port.string);
			ServerBootstrap bootstrap = ServerBootstrap(
				NioServerSocketChannelFactory(
					newCachedThreadPool(), 
					newCachedThreadPool()));

			bootstrap.pipelineFactory := HttpServerPipelineFactory();
			bootstrap.bind(InetSocketAddress(port));
			print("INFO: Server started");

		} else {
			print("ERROR: Specify port as the only argument");
		}
	}
//}