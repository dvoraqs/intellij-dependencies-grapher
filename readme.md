Purpose

I'm hoping for this to become an automated way of taking exports from dependency analysis in IntelliJ so that it can be consumed in Neo4j


Usage

`./regex.sh [input file] [output file]`


How is it supposed to work?

IntelliJ gives us an XML file. I found a tool that would take some input file and create a Neo4j database from that information. 
The trick is to convert the format of the data between XML and whatever format this tool expects. 
We might try to clean up the data so that we can reduce noise and try to focus on the important bits.


Dependency information

Using `sed` for the majority of regex replacement for now.

If `gsed` is used, that required installation on my MacOS. Do `brew install gnu-sed`.
    There seemed to be some shortcoming of `sed` that is fixed in `gsed`, but I don't know specifically what that is.
    When using `gsed` and changing files inline, use `--in-place` instead of `-i ""` as used in `sed`

`perl` is used for multiline regex replacements.