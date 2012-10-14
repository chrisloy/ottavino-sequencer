package com.cloy.ottavino.sequencer;

import java.util.ArrayList;
import java.util.List;

import com.cloy.ottavino.Chord;
import com.cloy.ottavino.Instrument;
import com.cloy.ottavino.Melody;

public abstract class Sequencer<I extends Instrument> {
	
	private final long interval;
	private final List<Sequence> sequences = new ArrayList<Sequence>();
	
	public Sequencer(int bpm) {
		this.interval = 60000 / bpm;
	}
	
	public void play(int beats) {
		for(int i=0; i<beats; i++) {
			for(Sequence s : sequences) {
				s.on(i);
			}
			try {
				Thread.sleep(interval);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			for(Sequence s : sequences) {
				s.off(i);
			}
		}
		try {
			Thread.sleep(interval);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	public void add(Sequence s) {
		sequences.add(s);
	}
	
	public abstract void add(I instrument, Chord...chords);
	
	public abstract void add(I instrument, Melody melody);
	
	public abstract void add(I instrument, int pan, Chord...chords);
	
	public abstract void add(I instrument, int pan, Melody melody);
}
