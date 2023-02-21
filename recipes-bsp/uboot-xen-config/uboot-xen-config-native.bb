SUMMARY="boot.scr generator for qemu arm hypercar demo"
DESCRIPTION = "${SUMMARY}"

LICENSE = "MIT"
SRC_URI = "file://imagebuilder.conf"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "xen ${MACHINE_DTB_PROVIDER} imagebuilder-native u-boot-mkimage-native dtc-native" 

inherit deploy
inherit native
## FIXME ugly!
do_compile[depends] += "${PN}:do_fetch \
		        dtc-native:do_populate_sysroot \
		        imagebuilder-native:do_populate_sysroot \
		        u-boot-mkimage-native:do_populate_sysroot"

## We don't need any of this
## noexec is not enough because it causes circular dependencies 
# deltask do_compile
deltask do_configure
deltask do_install

do_compile(){
	${STAGING_DIR_NATIVE}/usr/libexec/imagebuilder/uboot-script-gen -c ${WORKDIR}/imagebuilder.conf -d ${DEPLOY_DIR_IMAGE} -t virtio -o ${WORKDIR}/boot
}

do_deploy(){
        install -D -m 0644 ${WORKDIR}/boot.scr ${DEPLOYDIR}/boot.scr
}

addtask do_deploy after do_compile before do_build