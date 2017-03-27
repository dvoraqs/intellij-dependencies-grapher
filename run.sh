INPUT_FILE=${1:-"examples/shared_dependencies_original.xml"}
OUTPUT_FILE=${2:-"result.txt"}

mkdir run
TEMP_FILE="run/temp.json"
TEMP_FILE_2="run/temp2.txt"

echo "Building prerequisites..."
./scripts/prerequisites.sh

echo "Digesting input..."
./scripts/restructure_xml_to_json.sh $INPUT_FILE $TEMP_FILE
node scripts/restructure_json_to_other.js $TEMP_FILE > $TEMP_FILE_2
./scripts/cleanup_data.sh $TEMP_FILE_2 $OUTPUT_FILE

echo "Uploading graph..."
./scripts/write_graph.sh $OUTPUT_FILE

rm -rf run

echo "Done! Check it out: ${OUTPUT_FILE}"