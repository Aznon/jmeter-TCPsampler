package JMeter.plugins.functional.samplers.socketsamplerGui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.jmeter.util.JMeterUtils;


import JMeter.plugins.functional.samplers.socketsampler.TCPSampler;

public  class tcpsamplergui extends AbstractSamplerGui {
	
	public static ArgumentsPanel argsPanel;
	
	public tcpsamplergui() {
		super();
		setLayout(new BorderLayout(0, 5));
	    setBorder(makeBorder());
	    add(makeTitlePanel(), BorderLayout.NORTH);
	    JPanel jPanel = new JPanel(new BorderLayout(5,20));
	    
	    
	    
	    MYtcpsampler jp=new MYtcpsampler();
	    jPanel.add(jp,BorderLayout.NORTH);
	    jp.setPreferredSize(new Dimension(0, 50));
	    jPanel.add(createParameterPanel(),BorderLayout.CENTER);
	    add(jPanel,BorderLayout.CENTER);
	}
	

	@Override
	public String getStaticLabel() {
        return "TSL_socket_Sampler";
    }
	
	 @Override
	 public void configure(TestElement element) {
	        super.configure(element);
	        // N.B. this will be a config element, so we cannot use the getXXX() methods
	        MYtcpsampler.server.setText(element.getPropertyAsString(TCPSampler.SERVER));
	        // Default to original behaviour, i.e. re-use connection
	       // reUseConnection.setSelected(element.getPropertyAsBoolean(TCPSampler.RE_USE_CONNECTION, TCPSampler.RE_USE_CONNECTION_DEFAULT));
	        MYtcpsampler.port.setText(element.getPropertyAsString(TCPSampler.PORT));
	        MYtcpsampler.timeout.setText(element.getPropertyAsString(TCPSampler.TIMEOUT));
	        MYtcpsampler.connecttimeout.setText(element.getPropertyAsString(TCPSampler.TIMEOUT_CONNECT));
//	        MYtcpsampler.setproto(element.getPropertyAsString(TCPSampler.REQUEST));
	        argsPanel.configure((Arguments) element.getProperty(TCPSampler.ARGUMENTS).getObjectValue());
	        
	    }
	 
	  private JPanel createParameterPanel() {
	         argsPanel = new ArgumentsPanel("发送16进制数据"); 

	       // $NON-NLS-1$
	        return argsPanel;
	    }

	 
	 

	
	@Override
	public TestElement createTestElement() {
		// TODO Auto-generated method stub
		TCPSampler sampler = new TCPSampler();
        modifyTestElement(sampler);
        return sampler;
	}

	   @Override
	    public void clearGui() {
	        super.clearGui();

	        MYtcpsampler.server.setText("");
	        MYtcpsampler.port.setText("");
	        MYtcpsampler.timeout.setText("");
//	        MYtcpsampler.proto.setText("");
	        MYtcpsampler.connecttimeout.setText("");
	        argsPanel.clearGui();
	        
	    }
	@Override
	public String getLabelResource() {
		throw new IllegalStateException("This shouldn't be called");
	}

    @Override
    public void modifyTestElement(TestElement element) {
    	element.clear();
        configureTestElement(element);
        // N.B. this will be a config element, so we cannot use the setXXX() methods
//        element.setProperty(TCPSampler.CLASSNAME, classname.getText(), "");
        element.setProperty(TCPSampler.SERVER, MYtcpsampler.server.getText());
//        element.setProperty(TCPSampler.RE_USE_CONNECTION, reUseConnection.isSelected());
        element.setProperty(TCPSampler.PORT, MYtcpsampler.port.getText());
//        setNoDelay.setPropertyFromTristate(element, TCPSampler.NODELAY);
        element.setProperty(TCPSampler.TIMEOUT, MYtcpsampler.timeout.getText());
        element.setProperty(TCPSampler.TIMEOUT_CONNECT,MYtcpsampler.connecttimeout.getText());
        element.setProperty(new TestElementProperty(TCPSampler.ARGUMENTS, (Arguments) argsPanel.createTestElement()));
//        element.setProperty(TCPSampler.REQUEST,MYtcpsampler.jsonstring());
//        closeConnection.setPropertyFromTristate(element, TCPSampler.CLOSE_CONNECTION); // Don't use default for saving tristates
//        element.setProperty(TCPSampler.SO_LINGER, soLinger.getText(), "");
//        element.setProperty(TCPSampler.EOL_BYTE, eolByte.getText(), "");
    }

	
	

}
