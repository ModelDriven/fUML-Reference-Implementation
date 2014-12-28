###############################################################################
# SET THESE VALUES
###############################################################################

set -x # turn debugging on
# set +x # turn debugging off

OUTPUT_DIR=${1}
XJB_FILE=${2}
XSD_FILE=${3}

###############################################################################
# DO NOT EDIT BELOW THIS LINE
###############################################################################

LOG_FILE_PATH=$OUTPUT_DIR"/"$LOG_FILE

xjc -d $OUTPUT_DIR -extension -b $XJB_FILE $XSD_FILE #2>&1 | tee $LOG_FILE_PATH 