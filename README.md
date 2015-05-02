# kosuzu

Given a URL to a Touhou doujin album, this tool will generate a [Touhou wiki] [1] article for it.

This project is named after [Kosuzu Motoori] [2], who can decipher books by only touching them. Similarly, this project aims to decipher URLs to Touhou doujin albums.

## Usage

Simply pass the Touhou doujin album URL as an argument:

    $ java -jar kosuzu-0.1.0-standalone.jar http://e-ns.net/discography/ens0036.html

## Adding new parsers

If you want to add a parser for a doujin circle, first create a file for it under the `src/kosuzu/parser` directory. The contents of the file should be similar to the following (using [Yuuhei Satellite (幽閉サテライト)] [3] as an example):

    ; Lowercase the circle's romanized name in the namespace
    ; and remove spaces
    (ns kosuzu.parser.yuuheisatellite
      (:require kosuzu.parser))

    ; Camel-case the circle's romanized name in the defrecord
    (defrecord YuuheiSatelliteParser [html url]
      kosuzu.parser/Parser
      (can-parse-album?
        [this] ; You can access the html and url with (:html this)
               ; and (:url this) respectively
        true) ; Logic for determining whether this parser can parse
              ; the given html content and url

      (parse-album
        [this]
        {})) ; Logic that returns a hash map with key-value pairs
             ; representing the parameters defined in the URL below:
             ; http://en.touhouwiki.net/wiki/Template:MusicArticle

    (defn get-parser [html url] (YuuheiSatelliteParser. html url))

For more examples, please look at the existing parsers under the `src/kosuzu/parser` directory.

## License

The MIT License (MIT)

Copyright (c) 2015 Bryan Tsang

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

[1]: http://en.touhouwiki.net/wiki/ "Touhou Wiki"
[2]: http://en.touhouwiki.net/wiki/Kosuzu_Motoori "Kosuzu Motoori - Touhou Wiki"
[3]: http://www.yuuhei-satellite.jp/ "幽閉サテライト"
