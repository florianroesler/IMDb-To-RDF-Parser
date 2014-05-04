import java.util.Collection;
import java.util.LinkedList;

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.parser.Callback;
import org.semanticweb.yars.util.LRUMapCache;


public class LDSpiderCallback  implements Callback {
        final Collection<Quad> _out;

        long _cnt = 0;
        final boolean _close;
       
       
        public LRUMapCache<Node, ByteArray> _cache = new LRUMapCache<Node, ByteArray>();
        
        public LDSpiderCallback(Collection<Quad> out) {
                this(out, false);
        }
       
        public LDSpiderCallback(Collection<Quad> out, boolean close) {
                _out = out;
                _close = close;
        }
       
        public synchronized void processStatement(Node[] nx) {
        		_cnt++;
        		System.out.println("["+_cnt+"]");
        		this._out.add(new Quad(nx[3].toN3().toString(), nx[1].toN3().toString(), 
        							   nx[2].toN3().toString(), nx[0].toN3().toString()));
        		System.out.println(nx[3].toN3().toString());
        		System.out.println(nx[1].toN3().toString());       
        		System.out.println(nx[2].toN3().toString());
        		System.out.println(nx[0].toN3().toString());
        		System.out.println("-----------------------------------------------------------------------------------");  
                        
       
        }

        public void startDocument() {
         
        }
       
        public void endDocument() {
               
        }
       
        public String toString() {
                return _cnt + " tuples ";
        }
       
        public byte[] getBytes(Node n){
//              return n.toN3().getBytes();
                ByteArray ba = _cache.get(n);
                if(ba==null){
                        ba = new ByteArray();
                        ba._b = n.toN3().getBytes();
                        _cache.put(n, ba);
                }
                return ba._b;
        }
       
        public static class ByteArray{
                byte[] _b;
        }
}


