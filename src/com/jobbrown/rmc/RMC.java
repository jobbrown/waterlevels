package com.jobbrown.rmc;

import com.jobbrown.rmc.corba.RMCPOA;

public class RMC extends RMCPOA {
	@Override
	public String getHello() {
		return "Hello There";
	}
}
