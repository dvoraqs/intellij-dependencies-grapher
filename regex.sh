INPUT_FILE=${VAR1:-"shared_dependencies_original.xml"}
OUTPUT_FILE=${VAR2:-"result.xml"}

# Gets rid of lines for jar files
sed "s/.*\/src.zip!\/.*//g" $INPUT_FILE > $OUTPUT_FILE

echo "Check it out: ${OUTPUT_FILE}"