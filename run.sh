INPUT_FILE=${1:-"examples/shared_dependencies_original.xml"}
OUTPUT_FILE=${2:-"result.json"}

cp $INPUT_FILE $OUTPUT_FILE

./restructure_xml_to_json.sh $OUTPUT_FILE $OUTPUT_FILE