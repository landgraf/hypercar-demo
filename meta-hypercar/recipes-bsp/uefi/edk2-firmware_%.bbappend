# SPDX-FileCopyrightText: Huawei Inc.
#
# SPDX-License-Identifier: MIT

COMPATIBLE_MACHINE:qemuarm64-xen-efi = "${MACHINE}"
EDK2_PLATFORM      = "ArmVirtQemu-AARCH64"
EDK2_PLATFORM_DSC  = "ArmVirtPkg/ArmVirtQemu.dsc"
EDK2_BIN_NAME      = "QEMU_EFI.fd"
