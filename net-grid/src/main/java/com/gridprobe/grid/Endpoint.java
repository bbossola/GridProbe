package com.gridprobe.grid;

import com.gridprobe.grid.serialization.Binaryzable;

public interface Endpoint extends Comparable<Endpoint>, Binaryzable {

	public Lan lan()
	;
}
