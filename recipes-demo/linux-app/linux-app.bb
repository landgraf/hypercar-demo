SUMMARY = "Linux app to be monitored by RTOS"
DESCRIPTION="${SUMMARY}"

LICENSE="MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SRC_URI = "file://linux-app.c"

do_compile(){
    ${CC} ${LDFLAGS} ${WORKDIR}/linux-app.c -o ${B}/hypercar-app
}

do_install(){
	install -D -m 0755 ${B}/hypercar-app ${D}/usr/bin/hypercar-app
}