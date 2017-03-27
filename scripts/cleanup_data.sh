INPUT_FILE=${1:-"result.txt"}
OUTPUT_FILE=${2:-"result.txt"}

mkdir cleanup
TEMP_FILE="cleanup/temp.txt"
TEMP_FILE_2="cleanup/temp2.txt"
HEAD_PART="cleanup/head.txt"
OTHER_PARTS="cleanup/other.txt"

cp $INPUT_FILE $TEMP_FILE

# Ignore some pieces of information we may not need

sed -i "" 's/\$PROJECT_DIR\$\///g' $TEMP_FILE
sed -i "" 's/.*jdk\/.*//g' $TEMP_FILE
sed -i "" 's/[^"]*\/\([^\/]*\.jar\)![^"]*/\1/g' $TEMP_FILE

sed -i "" 's/\/src\/java\/com\/[^"]*.java//g' $TEMP_FILE
sed -i "" 's/\/src\/main\/java\/com\/[^"]*.java//g' $TEMP_FILE
sed -i "" 's/\/src\/test\/java\/com\/[^"]*.java//g' $TEMP_FILE
sed -i "" 's/\/test\/java\/com\/[^"]*.java//g' $TEMP_FILE

#sed -i "" 's/src\/main\/java\/com\///g' $TEMP_FILE
#sed -i "" 's/\/[^\/]*.java//g' $TEMP_FILE

# Clean up duplicates

head -n 1 $TEMP_FILE > $HEAD_PART
tail -n +2 $TEMP_FILE > $OTHER_PARTS
sort $OTHER_PARTS | uniq > $TEMP_FILE_2
cat $HEAD_PART $TEMP_FILE_2 > $OUTPUT_FILE

rm -rf cleanup