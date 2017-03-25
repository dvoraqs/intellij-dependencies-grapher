INPUT_FILE=${1:-"shared_dependencies_original.xml"}
OUTPUT_FILE=${2:-"result.xml"}

# Gets rid of lines for archived files
sed "s/.*\/src.zip!\/.*//g" $INPUT_FILE > $OUTPUT_FILE

echo "Check it out: ${OUTPUT_FILE}"