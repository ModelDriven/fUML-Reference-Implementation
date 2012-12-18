package org.modeldriven.fuml.library.channel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.modeldriven.fuml.library.common.Status;

import UMLPrimitiveTypes.UnlimitedNatural;
import fUML.Semantics.Classes.Kernel.StringValue;
import fUML.Semantics.Classes.Kernel.Value;

public class StandardInputChannelObject extends TextInputChannelObject {
	
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public String getName() {
		return "StandardOutput";
	}

	@Override
	public void open(Status errorStatus) {
		if (!this.isOpen()) {
			this.reader = new BufferedReader(new InputStreamReader(System.in));
		}
	}

	@Override
	public void close(Status errorStatus) {
		if (this.isOpen()) {
			try {
				this.reader.close();
			} catch (IOException e) {
			}
		}
		this.reader = null;
	}

	@Override
	public boolean isOpen() {
		return this.reader != null;
	}

	@Override
	public boolean hasMore() {
		boolean hasMore = false;
		
		if (this.isOpen()) {
			try {
				this.reader.mark(2);
				hasMore = this.reader.read() > 0;
				this.reader.reset();
			} catch (IOException e) {
			}
		}
		
		return hasMore;
	}

	@Override
	public Value read(Status errorStatus) {
		StringValue v = null;
		String s = this.readCharacter(errorStatus);
		if (s != null) {
			v = new StringValue();
			v.value = s;
			v.type = this.locus.factory.getBuiltInType("String");
		}
		return v;
	}

	@Override
	public Value peek(Status errorStatus) {
		StringValue v = null;
		String s = this.peekCharacter(errorStatus);
		if (s != null) {
			v = new StringValue();
			v.value = s;
			v.type = this.locus.factory.getBuiltInType("String");
		}
		return v;
	}

	@Override
	public String readCharacter(Status errorStatus) {
		if (this.isOpen()) {
			try {
				int c = this.reader.read();
				if (c == -1) {
					errorStatus.setStatus("StandardInputChannel", -2, "No input");
					return null;
				} else {
					return String.valueOf((char)c);
				}
			} catch (IOException e) {
				errorStatus.setStatus("StandardInputChannel", -100, e.getMessage());
				return null;
			}
		} else {
			errorStatus.setStatus("StandardInputChannel", -1, "Not open");
			return null;
		}
	}

	@Override
	public String peekCharacter(Status errorStatus) {
		if (this.isOpen()) {
			try {
				this.reader.mark(2);
				String s = this.readCharacter(errorStatus);
				if (s != null) {
					this.reader.reset();
				}
				return s;
			} catch (IOException e) {
				errorStatus.setStatus("StandardInputChannel", -100, e.getMessage());
				return null;
			}
		} else {
			errorStatus.setStatus("StandardInputChannel", -1, "Not open");
			return null;
		}
	}

	@Override
	public String readLine(Status errorStatus) {
		if (this.isOpen()) {
			if (this.hasMore()) {
				try {
					String result = this.reader.readLine();
					return result;
				} catch (IOException e) {
					errorStatus.setStatus("StandardInputChannel", -100, e.getMessage());
					return null;
				}
			} else {
				errorStatus.setStatus("StandardInputChannel", -2, "No input");
				return null;
			}
		} else {
			errorStatus.setStatus("StandardInputChannel", -1, "Not open");
			return null;
		}
	}

	@Override
	public Boolean readBoolean(Status errorStatus) {
		if (!this.isOpen()) {
			errorStatus.setStatus("StandardInputChannel", -1, "Not open");
			return null;			
		} else {
			char cbuf[] = new char[4];
			try {
				this.reader.mark(5);
			} catch (IOException e) {
				errorStatus.setStatus("StandardInputChannel", -100, e.getMessage());
				return null;
			}
			try {
				int n = this.reader.read(cbuf, 0, 4);
				if (n < 4) {
					errorStatus.setStatus("StandardInputChannel", -3, "Cannot convert");
					this.reader.reset();
					return null;
				} else {
					String s = String.valueOf(cbuf);
					if (s.equals("true")) {
						return true;
					} else if (s.equals("fals")) {
						n = this.reader.read();
						if (n > 0 && ((char)n) == 'e') {
							return false;
						} else {
							errorStatus.setStatus("StandardInputChannel", -3, "Cannot convert");
							this.reader.reset();
							return null;
						}
					} else {
						errorStatus.setStatus("StandardInputChannel", -3, "Cannot convert");
						this.reader.reset();
						return null;
					}
				}
			} catch (IOException e) {
				errorStatus.setStatus("StandardInputChannel", -100, e.getMessage());
				try {
					this.reader.reset();
				} catch (IOException e1) {
				}
				return null;
			}
		}
	}

	@Override
	public Integer readInteger(Status errorStatus) {
		if (!this.isOpen()) {
			errorStatus.setStatus("StandardInputChannel", -1, "Not open");
			return null;
		} else {
			try {
				boolean negate = false;
				this.reader.mark(2);
				int c = this.reader.read();
				
				if (c == ((int)'+') || c == ((int)'-')) {
					negate = ((char)c) == '-';
					this.reader.mark(1);
					c = this.reader.read();
				}
				
				Integer n = this.readNatural(c, errorStatus);
				if (n == null || !negate) {
					return n;
				} else {
					return -n;
				}
				
			} catch (IOException e) {
				errorStatus.setStatus("StandardInputChannel", -100, e.getMessage());
				return null;
			}
		}
	}

	@Override
	public UnlimitedNatural readUnlimitedNatural(Status errorStatus) {
		UnlimitedNatural u = null;
		
		if (!this.isOpen()) {
			errorStatus.setStatus("StandardInputChannel", -1, "Not open");
		} else {
			try {
				this.reader.mark(2);
				int c = this.reader.read();
				
				if (c == ((int)'*')) {
					u = new UnlimitedNatural();
					u.naturalValue = -1;
				} else {
					Integer n = this.readNatural(c, errorStatus);
					if (n != null) {
						u = new UnlimitedNatural();
						u.naturalValue = n;
					}
				}				
			} catch (IOException e) {
				errorStatus.setStatus("StandardInputChannel", -100, e.getMessage());
			}
		}
		
		return u;
	}
	
	private Integer readNatural(int c, Status errorStatus) {
		try {
			if (c < 0) {
				errorStatus.setStatus("StandardInputChannel", -2, "No Input");
				this.reader.reset();
				return null;
			} else if (c < ((int)'0') || c > ((int)'9')) {
				errorStatus.setStatus("StandardInputChannel", -3, "Cannot convert");
				this.reader.reset();
				return null;
			}
			
			int n = c - ((int)'0');
			
			while (true) {
				this.reader.mark(1);
				c = this.reader.read();
				
				if (c >= ((int)'0') && c <= ((int)'9')) {
					n = n * 10 + c - ((int)'0');
				} else {
					this.reader.reset();
					return n;
				}
			}
		} catch (IOException e) {
			errorStatus.setStatus("StandardInputChannel", -100, e.getMessage());
			return null;
		}
	}

	@Override
	public Value new_() {
		return (Value)new StandardInputChannelObject();
	}

}
