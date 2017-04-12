INPUT_FILE=${1:-"result.txt"}
OUTPUT_FILE=${2:-"result.txt"}

mkdir cleanup
TEMP_FILE="cleanup/temp.txt"
TEMP_FILE_2="cleanup/temp2.txt"
HEAD_PART="cleanup/head.txt"
OTHER_PARTS="cleanup/other.txt"

cp $INPUT_FILE $TEMP_FILE

# Just ignore PROJECT_DIR var
sed -i "" 's/\$PROJECT_DIR\$\///g' $TEMP_FILE

# For Jars, keep only jar name
sed -i "" 's/[^"]*\/\([^\/]*\.jar\)![^"]*/\1/g' $TEMP_FILE

# Ignore dependencies on standard Java libraries
sed -i "" 's/.*jdk\/.*//g' $TEMP_FILE

# Ignore dependencies on and from some file types
sed -E -i "" 's/.*\.(gradle|xml|groovy|jar|properties|wsdl|xsd).*//g' $TEMP_FILE

# Keep only project info -- discard package and class info
sed -i "" 's/\/generated-src\/[^"]*//g' $TEMP_FILE
sed -i "" 's/\/generated-sources\/[^"]*//g' $TEMP_FILE
sed -E -i "" 's/(\/src|\/java|\/com|\/test|\/main|\/validator|\/generator|\/generated|\/cucumber|\/groovy){2,}[^"]*//g' $TEMP_FILE

# Remove dependencies on self
sed -i "" 's/\("[^"]*"\) -> \1;//g' $TEMP_FILE

# Remove blank lines
sed -i "" '/^[[:space:]]*$/d' $TEMP_FILE

# Clean up duplicates
head -n 1 $TEMP_FILE > $HEAD_PART
tail -n +2 $TEMP_FILE > $OTHER_PARTS
sort $OTHER_PARTS | uniq > $TEMP_FILE_2
cat $HEAD_PART $TEMP_FILE_2 > $OUTPUT_FILE

rm -rf cleanup