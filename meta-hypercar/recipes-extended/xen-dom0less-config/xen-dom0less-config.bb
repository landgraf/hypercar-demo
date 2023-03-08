SUMMARY = "Systemd unit and script to attach block device to dom0less domain(s)"
DESCRIPTION="${SUMMARY}"

LICENSE="MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SRC_URI = "file://xen-hypercar-dom0less.sh \
	   file://xen-dom0less-config.service \
	   "

inherit systemd

do_configure[noexec]="1"
do_compile[noexec]="1"

do_install(){
	install -D -m 0755 ${WORKDIR}/xen-hypercar-dom0less.sh ${D}/usr/bin/xen-hypercar-dom0less.sh
	install -D -m 0644 ${WORKDIR}/xen-dom0less-config.service ${D}/${systemd_unitdir}/system/xen-dom0less-config.service

}
SYSTEMD_AUTO_ENABLE = "enable"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "xen-dom0less-config.service"

