## IRext Encoding and Decoding Core Library

  This project contains IRext core encode algorithm and decode algorithm.

### Summary

  IR code is base on 38000Hz or 56000Hz carrier waves, it is identified by the time interval of carrier waves.

  Identical control signal is defined by pairs of physical high and low levels.

  The lead code is identified by the 9000us time interval with carrier waves and 4500us interval without carrier waves. The logic 0 is identified by 500us time interval with carrier waves and 500us interval without carrier waves. The logic 1 is identified by 500us time interval with carrier waves and 1500us interval without carrier waves, so are the other types of encoding.

Following are some specifications:

- Physical high level is constituted by carrier waves and physical low level without any carrier waves.
- Basic protocol is constructed by different types of elements including lead code, end code, splitter, logic 0 and logic 1, different protocol has its own element structure, eg. NEC, upd6121g, tc9012, etc.
- 1 or more cycles resists in single element, but often 1 cycle.
- Lead code, end code and splitter takes 1 bit in element, for example, in NEC above, a '9000-4500' indicates 1 logic bit.
- Logic 0 and logic 1 take 8 bit in an element.
- Logic 0 and logic 1 could be big endian or little endian.
- For some of the protocols, logic 0 and logic 1 have bit masks.
- For some of the protocols, like mi-1 pattern could be in format of binary, quaternary or hexadecimal.
- For some of the protocols, like RC5 toggles its bits.
- For some of the protocols, there are 0us time intervals in lead code or end code.

2 kinds of remote control IR code:

- Command code: The receiver receives IR code as commands from emitter, and then adjusts status of itself, eg. control code of TV, DVD.
- Status code: The receiver receives IR code as status, and applies itself to this status, eg. control code of air conditioner.

### Encoder

  The encoder composes compressed remote control IR binaries from plain text in XML format. Different kind of remote control code are differently composed.

__Command code__

  Command code is composed by 2 parts : protocol segment and control segment which are stored in 2 parts. When encode, the remote control key code would be calculated and translated according to protocol definition and merge separate segments into a complete control frame.

__Status code__

  Status code is defined by various kinds of tags, indication each essential part in one status control frame. It is especially used for air conditioners.

### Decoder

  The decoder decodes IR remote control binaries into IR control frame which is composed by time series illustrated above.

  The input of decode algorithm is the IR remote control binary file or its content in bytes, the keycode and category id get from indexing API. 

  The output of the algorithm is the time series, which can be sent to IR emit driver function to send IR control waves to the family equipment.

  Please refer to https://site.irext.net/doc/ for details.


