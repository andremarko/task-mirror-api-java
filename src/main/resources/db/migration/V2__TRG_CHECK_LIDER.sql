CREATE OR REPLACE TRIGGER bfi_trg_check_lider
BEFORE INSERT OR UPDATE ON tbl_usuarios
                            FOR EACH ROW
DECLARE
v_role VARCHAR2(100);
BEGIN
    IF :NEW.id_lider IS NOT NULL THEN
SELECT role_usuario
INTO v_role
FROM tbl_usuarios
WHERE id_usuario = :NEW.id_lider;

IF v_role != 'ROLE_SUPERIOR' THEN
            RAISE_APPLICATION_ERROR(
                -20001,
                'Não é permitido associar um líder que não tenha ROLE_SUPERIOR.'
            );
END IF;
END IF;
END;
/
