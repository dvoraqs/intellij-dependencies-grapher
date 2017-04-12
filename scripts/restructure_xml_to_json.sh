INPUT_FILE=${1:-"examples/dependencies.xml"}
OUTPUT_FILE=${2:-"result.json"}

if [ $INPUT_FILE != $OUTPUT_FILE ]
    then 
        cp $INPUT_FILE $OUTPUT_FILE
fi

# Removes xml tag
sed -i "" 's/<\?xml version="1.0" encoding="UTF-8"\?>//g' $OUTPUT_FILE

# Changes root tag
sed -i "" 's/<root.*>/[/g' $OUTPUT_FILE
sed -i "" 's/<\/root>/]/g' $OUTPUT_FILE

# Changes file tag
sed -i "" 's/<file path="\(.*\)" \/>/{"name": "\1", "size": 1, "imports": []},/g' $OUTPUT_FILE
sed -i "" 's/<file path="\(.*\)">/{"name": "\1", "size": 1, "imports": [/g' $OUTPUT_FILE
sed -i "" 's/<\/file>/]},/g' $OUTPUT_FILE

# Changes dependency tags
sed -i "" 's/<dependency path=//g' $OUTPUT_FILE
sed -i "" 's/ \/>/,/g' $OUTPUT_FILE

# Remove dangling commas for lists
perl -0777pe 's/,(\s*)]/$1]/gm' -i $OUTPUT_FILE

# Remove blank lines
perl -0777pe 's/[\n\r]+$|^[\n\r]+//gm' -i $OUTPUT_FILE