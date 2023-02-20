SUMMARY = "Qemu Device Tree Binary"
## TODO this should be changed to get dtb from uboot/kernel or
## generate in build time
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

inherit qemuboot
inherit deploy
inherit native


S = "${WORKDIR}"

DEPENDS = "qemu-helper-native"

do_configure[noexec] = "1"
do_deploy[nostamp] = "1"


do_compile(){
    ${QB_SYSTEM_NAME} \
        -device qemu-xhci \
        -device usb-tablet \
        -device usb-kbd \
        ${QB_MACHINE} \
        ${QB_CPU} \
        ${QB_SMP} \
        ${QB_MEM} \
        -nographic \
        -serial mon:stdio \
        -machine "dumpdtb=${B}/qemu-dumped.dtb"
}

do_install(){
	install -D -m 0644 ${B}/qemu-dumped.dtb ${D}/usr/share/qemu-dtb/qemu-arm64_generated.dtb
}

do_deploy(){
	install -D -m 0644 ${B}/qemu-dumped.dtb ${DEPLOYDIR}/qemu-arm64_generated.dtb
}

addtask deploy after do_install before do_build

ALLOW_EMPTY:${PN} = "1"
