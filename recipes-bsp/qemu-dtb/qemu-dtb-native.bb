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


do_compile(){
# runqemu - INFO - Running /mnt/builds/yocto/builds/build-suse/tmp-glibc/work/x86_64-linux/qemu-helper-native/1.0-r1/recipe-sysroot-native/usr/bin/qemu-system-aarch64 -device virtio-net-pci,netdev=net0,mac=52:54:00:12:34:02 -netdev tap,id=net0,ifname=tap0,script=no,downscript=no -object rng-random,filename=/dev/urandom,id=rng0 -device virtio-rng-pci,rng=rng0 -drive id=disk0,file=/mnt/builds/yocto/builds/build-suse/tmp-glibc/deploy/images/qemuarm64-xen-uboot/hypercar-image-demo-qemuarm64-xen-uboot.wic,if=none,format=raw -device virtio-blk-device,drive=disk0   -machine virtualization=true -machine virt,gic_version=3 -cpu cortex-a57 -smp 4 -m 4096 -serial mon:vc -serial null -display sdl,show-cursor=on  -bios /mnt/builds/yocto/builds/build-suse/tmp-glibc/deploy/images/qemuarm64-xen-uboot/u-boot-qemuarm64-xen-uboot.bin 

    ${QB_SYSTEM_NAME} \
        -device virtio-rng-pci \
	-drive id=disk0,file=/dev/null,if=none,format=raw \
	-device virtio-blk-device,drive=disk0 \
        -device qemu-xhci \
	-device virtio-net-pci\
        -device usb-tablet \
        -device usb-kbd \
        ${QB_MACHINE} \
        ${QB_CPU} \
        ${QB_SMP} \
        ${QB_MEM} \
        -nographic \
        -serial mon:stdio \
        -machine "dumpdtb=${B}/qemu-dumped.dtb"
    dtc -I dtb -O dts ${B}/qemu-dumped.dtb > ${B}/qemu-dumped.dts
    sed 's/compatible = \"arm,pl061.*/status = \"disabled\";/g' -i ${B}/qemu-dumped.dts
    dtc -I dts -O dtb ${B}/qemu-dumped.dts > ${B}/qemu-dumped.dtb
}

do_install(){
        install -D -m 0644 ${B}/qemu-dumped.dts ${D}/usr/share/qemu-dtb/qemu-arm64_generated.dts
	install -D -m 0644 ${B}/qemu-dumped.dtb ${D}/usr/share/qemu-dtb/qemu-arm64_generated.dtb
}

do_deploy(){
	install -D -m 0644 ${B}/qemu-dumped.dtb ${DEPLOYDIR}/qemu-arm64_generated.dtb
	install -D -m 0644 ${B}/qemu-dumped.dts ${DEPLOYDIR}/qemu-arm64_generated.dts
}

addtask deploy after do_install before do_build

ALLOW_EMPTY:${PN} = "1"
