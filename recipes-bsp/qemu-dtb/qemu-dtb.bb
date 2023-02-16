SUMMARY = "Qemu Device Tree Binary"
DESCRIPTION = "Qemu Device tree \
Obtained with: \
   qemu-system-aarch64    -machine virt,gic_version=3 \
   -machine virtualization=true    -cpu cortex-a57 \
   -machine type=virt    -smp 4 -m 4096 -display none \
   -machine dumpdtb=virt-gicv3.dtb \
"

## FIXME
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://virt-gicv3.dtb"

inherit deploy
require conf/image-uefi.conf
S = "${WORKDIR}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install(){
        install -d ${D}/${EFI_FILES_PATH}
	install -m 0644 virt-gicv3.dtb ${D}/${EFI_FILES_PATH}/qemu-aarch64-gicv3.dtb
}

do_deploy(){
	install -D -m 0644 ${D}/${EFI_FILES_PATH}/qemu-aarch64-gicv3.dtb ${DEPLOYDIR}
}
## FIXME
addtask deploy after do_install before do_build


FILES:${PN} = "${EFI_FILES_PATH}/qemu-aarch64-*.dtb"

PACKAGE_ARCH = "${MACHINE_ARCH}"