import Swal from "sweetalert2";

export function ConfirmAlert(title: string, message?: string) {
    return function (target: any, propertyKey: string, descriptor: PropertyDescriptor) {
        const originalFn = descriptor.value as any;

        (descriptor.value as any) = function (this: any, ...args: any[]) {
            Swal.fire({
                title: title,
                text: message,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Continuar',
                cancelButtonText: 'Cancelar',
            }).then((result) => {
                if (result.isConfirmed) {
                    originalFn.apply(this, args);
                }
            })
        };
    
        return descriptor;
    };
}