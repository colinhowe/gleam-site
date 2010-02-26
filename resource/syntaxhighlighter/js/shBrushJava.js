/**
 * SyntaxHighlighter
 * http://alexgorbatchev.com/
 *
 * SyntaxHighlighter is donationware. If you are using it, please donate.
 * http://alexgorbatchev.com/wiki/SyntaxHighlighter:Donate
 *
 * @version
 * 2.1.364 (October 15 2009)
 * 
 * @copyright
 * Copyright (C) 2004-2009 Alex Gorbatchev.
 *
 * @license
 * This file is part of SyntaxHighlighter.
 * 
 * SyntaxHighlighter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * SyntaxHighlighter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with SyntaxHighlighter.  If not, see <http://www.gnu.org/copyleft/lesser.html>.
 */
 
    
SyntaxHighlighter.brushes.Java = function() {
  function thirdMatch(match, regexInfo) {
    return [new SyntaxHighlighter.Match(match[2], match.index + match[1].length, regexInfo.css)];
  };
  
  function secondMatch(match, regexInfo) {
    return [new SyntaxHighlighter.Match(match[1], match.index, regexInfo.css)];
  };
  
	var keywords =	'macro with node cascade var';

	this.regexList = [
		{ regex: SyntaxHighlighter.regexLib.singleLineCComments,	css: 'comments' },		// one line comments
    { regex: /(""(([^"])|("[^"]))*"")|("[^"\n\r]+")/gm,   css: 'string' },    // strings
		{ regex: /\/\*([^\*][\s\S]*)?\*\//gm,						css: 'comments' },	 	// multiline comments
		{ regex: /\b([\d]+(\.[\d]+)?|0x[a-f0-9]+)\b/gi,				css: 'value' },			// numbers
		{ regex: /([a-zA-Z0-9]+)\s*: /gm,				css: 'variable', func: secondMatch },			// parameter names
		{ regex: new RegExp(this.getKeywords(keywords), 'gm'),		css: 'keyword' },		// java keyword
    { regex: /top level|restrict to/gm,    css: 'keyword' },    // java keyword
		];

	this.forHtmlScript({
		left	: /(&lt;|<)%[@!=]?/g, 
		right	: /%(&gt;|>)/g 
	});
};

SyntaxHighlighter.brushes.Java.prototype	= new SyntaxHighlighter.Highlighter();
SyntaxHighlighter.brushes.Java.aliases		= ['java'];
